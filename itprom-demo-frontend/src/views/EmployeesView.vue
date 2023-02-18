<template>
  <div>
    <table v-if="items.length > 0">
      <thead>
      <tr>
        <th>ФИО</th><th>Профессия</th><th>Отдел</th><th>Примечание</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="item in items"
          v-bind:key="item"
          style="cursor: pointer"
          v-bind:class="{selected: item.selected}"
          v-on:click="selectItem">
        <td>{{item.fullName}}</td>
        <td>{{item.profession ? item.profession.name : '(пусто)'}}</td>
        <td>{{item.department ? item.department.name : '(пусто)'}}</td>
        <td><i>{{item.note}}</i></td>
      </tr>
      </tbody>
    </table>
    <div v-else>
      <span>(Пусто)</span>
    </div>
    <button v-on:click="openCreator">Добавить элемент</button>
    <button v-on:click="openEditor" v-if="selectedItemExists">Редактировать</button>
    <button v-on:click="deleteItem" v-if="selectedItemExists">Удалить</button>
    <div v-if="editor">
      <div>
        <label for="edit-item-full-name">ФИО:</label>
        <input id="edit-item-full-name" type="text" v-model="editor.fullName">
      </div>
      <div>
        <label for="edit-item-profession">Профессия:</label>
        <select id="edit-item-profession" v-model="editor.profession">
          <option v-for="profession in professions"
                  v-bind:key="profession"
                  v-bind:value="profession">
            {{profession.name}}
          </option>
        </select>
      </div>
      <div>
        <label for="edit-item-department">Отдел:</label>
        <select id="edit-item-department" v-model="editor.department">
          <option v-for="department in departments"
                  v-bind:key="department"
                  v-bind:value="department">
            {{department.name}}
          </option>
        </select>
      </div>
      <div>
        <label for="edit-item-note">Примечание:</label>
        <input id="edit-item-note" type="text" v-model="editor.note">
      </div>
      <button v-if="editor.status === 'create'" v-on:click="createItem">Создать</button>
      <button v-if="editor.status === 'update'" v-on:click="updateItem">Сохранить</button>
      <button v-on:click="closeEditor">Отмена</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "EmployeesView",
  data: () => ({
    items: [],
    professions: [],
    departments: [],
    editor: null,
    selectedItemIndex: -1
  }),
  methods: {
    openEditor: function () {
      let selectedItem = this.items[this.selectedItemIndex];
      this.editor = {id: selectedItem.id,
        fullName: selectedItem.fullName,
        profession: selectedItem.profession,
        department: selectedItem.department,
        note: selectedItem.note,
        status: 'update'};
    },
    openCreator: function () {
      this.editor = {fullName:'', profession: null, department: null, note: '', status: 'create'};
    },
    closeEditor: function () {
      this.editor = null;
    },
    selectItem: function (evt) {
      if (this.selectedItemIndex >= 0) {
        this.items[this.selectedItemIndex].selected = false;
      }
      this.selectedItemIndex = evt.currentTarget.sectionRowIndex;
      this.items[this.selectedItemIndex].selected = true;
    },
    fetchEmployees: function () {
      return this.$http.get('/api/employee');
    },
    createItem: function () {
      this.$http.post('/api/employee', this.editor).then(() => {
        this.closeEditor();
        this.refreshItems();
      });
    },
    updateItem: function () {
      this.$http.put(`/api/employee/${this.editor.id}`, this.editor).then(() => {
        this.closeEditor();
        this.refreshItems();
      });
    },
    deleteItem: function () {
      let id = this.items[this.selectedItemIndex].id;
      this.$http.delete(`/api/employee/${id}`).then(() => {
        this.refreshItems();
      }, (response) => {
        alert(response.body.detail);
      });
    },
    refreshItems: function () {
      this.fetchEmployees().then(response => {
        if (this.selectedItemIndex >= 0 && response.body.length) {
          this.selectedItemIndex = this.selectedItemIndex >= response.body.length ?
              response.body.length - 1 :
              this.selectedItemIndex;
          response.body[this.selectedItemIndex].selected = true;
        }
        this.items = response.body;
      })
    },
    loadProfessions: function () {
      this.$http.get('api/profession').then((response) => {
        this.professions = response.body;
      });
    },
    loadDepartments: function () {
      this.$http.get('api/department').then((response) => {
        this.departments = response.body;
      });
    },
  },
  computed: {
    selectedItemExists: function () {
      return this.selectedItemIndex >= 0 && this.items.length > 0;
    }
  },
  created: function () {
    this.refreshItems();
    this.loadProfessions();
    this.loadDepartments();
  }
}
</script>

<style scoped>
  .selected {
    background-color: #cce6ff;
  }
</style>