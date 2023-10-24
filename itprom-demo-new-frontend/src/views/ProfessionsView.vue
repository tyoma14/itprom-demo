<template>
  <div>
    <table v-if="professions.length > 0">
      <thead>
      <tr>
        <th>Наименование</th><th>Примечание</th>
      </tr>
      </thead>
      <tbody>
        <tr v-for="profession in professions"
            v-bind:key="profession"
            style="cursor: pointer"
            v-bind:class="{selected: profession.selected}"
            v-on:click="selectItem">
          <td><b>{{profession.name}}</b></td>
          <td><i>{{profession.note}}</i></td>
        </tr>
      </tbody>
    </table>
    <div v-else>
      <span>(Пусто)</span>
    </div>
    <button v-on:click="openCreator">Добавить элемент</button>
    <button v-on:click="openEditor" v-if="selectedItemExists">Редактировать</button>
    <button v-on:click="deleteProfession" v-if="selectedItemExists">Удалить</button>
    <div v-if="editor">
      <div>
        <label for="edit-profession-name">Наименование:</label>
        <input id="edit-profession-name" type="text" :value="name" @input="onNameChange">
      </div>
      <div>
        <label for="edit-profession-note">Примечание:</label>
        <input id="edit-profession-note" type="text" :value="note" @input="onNoteChange">
      </div>
      <button v-if="editorStatus === 'create'" v-on:click="createProfession">Создать</button>
      <button v-if="editorStatus === 'update'" v-on:click="updateProfession">Сохранить</button>
      <button v-on:click="closeEditor">Отмена</button>
    </div>
  </div>
</template>

<script lang="ts">

import {defineComponent} from "vue";
import axios, {AxiosResponse} from "axios";

interface ProfessionsViewData {
  professions: Profession[],
  editor: ProfessionEditor | null,
  selectedItemIndex: number,
  editorVisible: boolean
}

interface Profession {
  id: number | null,
  name: string,
  note: string,
  selected: boolean
}

interface ProfessionEditor extends Profession {
  status: EditorStatus
}

type EditorStatus = 'update' | 'create'

export default defineComponent({
      name: "ProfessionsView",
      data: (): ProfessionsViewData => ({
        professions: [],
        editor: null,
        selectedItemIndex: -1,
        editorVisible: false
      }),
      methods: {
        openEditor: function () {
          let selectedProfession = this.professions[this.selectedItemIndex];
          this.editor = {
            id: selectedProfession.id,
            name: selectedProfession.name,
            note: selectedProfession.note,
            selected: false,
            status: 'update'
          };
        },
        openCreator: function () {
          this.editor = {id: null, name:'', note:'', selected: false, status: 'create'};
        },
        closeEditor: function () {
          this.editor = null;
        },
        selectItem: function (evt: Event) {
          if (this.selectedItemIndex >= 0) {
            this.professions[this.selectedItemIndex].selected = false;
          }
          this.selectedItemIndex = (evt.currentTarget as HTMLTableRowElement)?.sectionRowIndex;
          this.professions[this.selectedItemIndex].selected = true;
        },
        fetchProfessions: function (): Promise<AxiosResponse<Profession[]>> {
          return axios.get('/api/profession');
        },
        createProfession: function () {
          axios.post('/api/profession', this.editor).then(() => {
            this.closeEditor();
            this.refreshProfessions();
          });
        },
        updateProfession: function () {
          axios.put(`/api/profession/${this.editor?.id}`, this.editor).then(() => {
            this.closeEditor();
            this.refreshProfessions();
          });
        },
        deleteProfession: function () {
          let id = this.professions[this.selectedItemIndex].id;
          axios.delete(`/api/profession/${id}`).then(() => {
            this.refreshProfessions();
          }, (response) => {
            alert(response.body.detail);
          });
        },
        refreshProfessions: function () {
          this.fetchProfessions().then(response => {
            if (this.selectedItemIndex >= 0 && response.data.length) {
              this.selectedItemIndex = this.selectedItemIndex >= response.data.length ?
                  response.data.length - 1 :
                  this.selectedItemIndex;
              response.data[this.selectedItemIndex].selected = true;
            }
            this.professions = response.data;
          })
        },
        onNameChange(evt: Event) {
          if (this.editor != null) {
            this.editor.name = (evt.target as HTMLInputElement).value
          }
        },
        onNoteChange(evt: Event) {
          if (this.editor != null) {
            this.editor.note = (evt.target as HTMLInputElement).value
          }
        }
      },
      computed: {
        selectedItemExists: function () {
          return this.selectedItemIndex >= 0 && this.professions.length > 0;
        },
        editorStatus(): EditorStatus | undefined {
          return this.editor?.status
        },
        name() {
          return this.editor?.name
        },
        note() {
          return this.editor?.note
        }
      },
      created: function () {
        this.refreshProfessions();
      }
    }
)

</script>

<style scoped>
  .selected {
    background-color: #cce6ff;
  }
</style>