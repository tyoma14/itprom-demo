var app = new Vue({
    el: '#app',
    data: {
        departments: [],
        editor: null,
        selectedItemIndex: -1
    },
    methods: {
        openEditor: function () {
            var selectedDepartment = this.departments[this.selectedItemIndex];
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
            this.$http.post('/api/department', this.editor).then(response => {
                this.closeEditor();
                this.refreshDepartments();
            });
        },
        updateDepartment: function () {
            this.$http.put(`/api/department/${this.editor.id}`, this.editor).then(response => {
                this.closeEditor();
                this.refreshDepartments();
            });
        },
        deleteDepartment: function () {
            var id = this.departments[this.selectedItemIndex].id;
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
            var childrenIds = [];
            this.departments.forEach(d => {
                if (d.parent && (d.parent.id === this.editor.id || childrenIds.includes(d.parent.id))) {
                    childrenIds.push(d.id);
                }
            });
            return this.departments.filter(d => d.id != this.editor.id && !childrenIds.includes(d.id));
        }
    },
    created: function () {
        this.refreshDepartments();
    }
})