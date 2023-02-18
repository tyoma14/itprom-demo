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
        <input id="edit-profession-name" type="text" v-model="editor.name">
      </div>
      <div>
        <label for="edit-profession-note">Примечание:</label>
        <input id="edit-profession-note" type="text" v-model="editor.note">
      </div>
      <button v-if="editor.status === 'create'" v-on:click="createProfession">Создать</button>
      <button v-if="editor.status === 'update'" v-on:click="updateProfession">Сохранить</button>
      <button v-on:click="closeEditor">Отмена</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "ProfessionsView",
  data: () => ({
    professions: [],
    editor: null,
    selectedItemIndex: -1
  }),
  methods: {
    openEditor: function () {
      let selectedProfession = this.professions[this.selectedItemIndex];
      this.editor = {id: selectedProfession.id,
        name: selectedProfession.name,
        note: selectedProfession.note,
        status: 'update'};
    },
    openCreator: function () {
      this.editor = {name:'', note:'', status: 'create'};
    },
    closeEditor: function () {
      this.editor = null;
    },
    selectItem: function (evt) {
      if (this.selectedItemIndex >= 0) {
        this.professions[this.selectedItemIndex].selected = false;
      }
      this.selectedItemIndex = evt.currentTarget.sectionRowIndex;
      this.professions[this.selectedItemIndex].selected = true;
    },
    fetchProfessions: function () {
      return this.$http.get('/api/profession');
    },
    createProfession: function () {
      this.$http.post('/api/profession', this.editor).then(() => {
        this.closeEditor();
        this.refreshProfessions();
      });
    },
    updateProfession: function () {
      this.$http.put(`/api/profession/${this.editor.id}`, this.editor).then(() => {
        this.closeEditor();
        this.refreshProfessions();
      });
    },
    deleteProfession: function () {
      let id = this.professions[this.selectedItemIndex].id;
      this.$http.delete(`/api/profession/${id}`).then(() => {
        this.refreshProfessions();
      }, (response) => {
        alert(response.body.detail);
      });
    },
    refreshProfessions: function () {
      this.fetchProfessions().then(response => {
        if (this.selectedItemIndex >= 0 && response.body.length) {
          this.selectedItemIndex = this.selectedItemIndex >= response.body.length ?
              response.body.length - 1 :
              this.selectedItemIndex;
          response.body[this.selectedItemIndex].selected = true;
        }
        this.professions = response.body;
      })
    }
  },
  computed: {
    selectedItemExists: function () {
      return this.selectedItemIndex >= 0 && this.professions.length > 0;
    }
  },
  created: function () {
    this.refreshProfessions();
  }
}
</script>

<style scoped>
  .selected {
    background-color: #cce6ff;
  }
</style>