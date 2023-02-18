<template>
  <div>
    <table v-if="departments.length > 0">
      <thead>
      <tr>
        <th>Наименование</th><th>Примечание</th><th>Родительский отдел</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="department in departments"
          v-bind:key="department"
          style="cursor: pointer"
          v-bind:class="{selected: department.selected}"
          v-on:click="selectItem">
        <td>{{department.name}}</td>
        <td><i>{{department.note}}</i></td>
        <td>{{department.parent ? department.parent.name : '(пусто)'}}</td>
      </tr>
      </tbody>
    </table>
    <div v-else>
      <span>(Пусто)</span>
    </div>
    <button v-on:click="openCreator">Добавить элемент</button>
    <button v-on:click="openEditor" v-if="selectedItemExists">Редактировать</button>
    <button v-on:click="deleteDepartment" v-if="selectedItemExists">Удалить</button>
    <div v-if="editor">
      <div>
        <label for="edit-department-name">Наименование:</label>
        <input id="edit-department-name" type="text" v-model="editor.name">
      </div>
      <div>
        <label for="edit-department-note">Примечание:</label>
        <input id="edit-department-note" type="text" v-model="editor.note">
      </div>
      <div>
        <label for="edit-department-parent">Родительский отдел:</label>
        <select id="edit-department-parent" v-model="editor.parent">
          <option v-for="department in possibleParents"
                  v-bind:key="department"
                  v-bind:value="department">
            {{department.name}}
          </option>
        </select>
      </div>
      <button v-if="editor.status === 'create'" v-on:click="createDepartment">Создать</button>
      <button v-if="editor.status === 'update'" v-on:click="updateDepartment">Сохранить</button>
      <button v-on:click="closeEditor">Отмена</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "DepartmentsView",
  data: () => ({
    departments: [],
    editor: null,
    selectedItemIndex: -1
  }),
  methods: {
    openEditor: function () {
      let selectedDepartment = this.departments[this.selectedItemIndex];
      this.editor = {id: selectedDepartment.id,
        name: selectedDepartment.name,
        note: selectedDepartment.note,
        parent: selectedDepartment.parent,
        status: 'update'};
    },
    openCreator: function () {
      this.editor = {name:'', note:'', parent: null, status: 'create'};
    },
    closeEditor: function () {
      this.editor = null;
    },
    selectItem: function (evt) {
      if (this.selectedItemIndex >= 0) {
        this.departments[this.selectedItemIndex].selected = false;
      }
      this.selectedItemIndex = evt.currentTarget.sectionRowIndex;
      this.departments[this.selectedItemIndex].selected = true;
    },
    fetchDepartments: function () {
      return this.$http.get('/api/department');
    },
    createDepartment: function () {
      this.$http.post('/api/department', this.editor).then(() => {
        this.closeEditor();
        this.refreshDepartments();
      });
    },
    updateDepartment: function () {
      this.$http.put(`/api/department/${this.editor.id}`, this.editor).then(() => {
        this.closeEditor();
        this.refreshDepartments();
      });
    },
    deleteDepartment: function () {
      let id = this.departments[this.selectedItemIndex].id;
      this.$http.delete(`/api/department/${id}`).then(() => {
        this.refreshDepartments();
      }, (response) => {
        alert(response.body.detail);
      });
    },
    refreshDepartments: function () {
      this.fetchDepartments().then(response => {
        if (this.selectedItemIndex >= 0 && response.body.length) {
          this.selectedItemIndex = this.selectedItemIndex >= response.body.length ?
              response.body.length - 1 :
              this.selectedItemIndex;
          response.body[this.selectedItemIndex].selected = true;
        }
        this.departments = response.body;
      })
    }
  },
  computed: {
    selectedItemExists: function () {
      return this.selectedItemIndex >= 0 && this.departments.length > 0;
    },
    possibleParents: function () {
      // предотвращаем создание циклических графов
      let childrenIds = [];
      this.departments.forEach(d => {
        if (d.parent && (d.parent.id === this.editor.id || childrenIds.includes(d.parent.id))) {
          childrenIds.push(d.id);
        }
      });
      return this.departments.filter(d => d.id !== this.editor.id && !childrenIds.includes(d.id));
    }
  },
  created: function () {
    this.refreshDepartments();
  }
}
</script>

<style scoped>
  .selected {
    background-color: #cce6ff;
  }
</style>