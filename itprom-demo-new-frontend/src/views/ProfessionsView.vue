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
    <a-button v-on:click="openCreator">Добавить элемент</a-button>
    <a-button v-on:click="openEditor" v-if="selectedItemExists">Редактировать</a-button>
    <a-button v-on:click="deleteProfession" v-if="selectedItemExists">Удалить</a-button>
    <div v-if="editor">
      <div>
        <label for="edit-profession-name">Наименование:</label>
        <input id="edit-profession-name" type="text" :value="name" @input="onNameChange"/>
      </div>
      <div>
        <label for="edit-profession-note">Примечание:</label>
        <input id="edit-profession-note" type="text" :value="note" @input="onNoteChange">
      </div>
      <a-button v-if="editorStatus === 'create'" v-on:click="createProfession">Создать</a-button>
      <a-button v-if="editorStatus === 'update'" v-on:click="updateProfession">Сохранить</a-button>
      <a-button v-on:click="closeEditor">Отмена</a-button>
    </div>
  </div>
</template>

<script lang="ts">

import {defineComponent} from "vue";
import Profession from "@/model/Profession";
import {useDirectoryStore} from "@/store/directory";
import {professionService} from "@/service/ProfessionService";

interface ProfessionEditor {
  value: Profession
  status: EditorStatus
}

type EditorStatus = 'update' | 'create'

export default defineComponent({
  name: "ProfessionsView",
  setup() {
    return {
      directoryStore: useDirectoryStore()
    }
  },
  data: () => ({
    editor: null as (ProfessionEditor | null),
    selectedItemIndex: -1,
    editorVisible: false
  }),
  methods: {
    openEditor: function () {
      let selectedProfession = this.professions[this.selectedItemIndex];
      this.editor = {
        value: new Profession(selectedProfession),
        status: 'update'
      };
    },
    openCreator: function () {
      this.editor = {value: new Profession(), status: 'create'};
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
    createProfession: function () {
      if (this.editor?.value) {
        professionService.create(this.editor.value).then(() => {
          this.closeEditor();
          this.refreshProfessions();
        });
      }
    },
    updateProfession: function () {
      if (this.editor?.value?.id) {
        professionService.update(this.editor.value.id, this.editor.value).then(() => {
          this.closeEditor();
          this.refreshProfessions();
        });
      }
    },
    deleteProfession: function () {
      let id = this.professions[this.selectedItemIndex].id;
      if (id) {
        professionService.delete(id).then(() => {
          this.refreshProfessions();
        }, (error: Error) => {
          alert(error.message);
        });
      }
    },
    refreshProfessions: function () {
      professionService.getAll().then(professions => {
        if (this.selectedItemIndex >= 0 && professions.length) {
          this.selectedItemIndex = this.selectedItemIndex >= professions.length ?
              professions.length - 1 :
              this.selectedItemIndex;
          professions[this.selectedItemIndex].selected = true;
        }
        this.directoryStore.setProfessions(professions)
      })
    },
    onNameChange(evt: Event) {
      if (this.editor != null) {
        this.editor.value.name = (evt.target as HTMLInputElement).value
      }
    },
    onNoteChange(evt: Event) {
      if (this.editor != null) {
        this.editor.value.note = (evt.target as HTMLInputElement).value
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
      return this.editor?.value.name
    },
    note() {
      return this.editor?.value.note
    },
    professions() {
      return this.directoryStore.professions
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