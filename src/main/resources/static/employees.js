var app = new Vue({
    el: '#app',
    data: {
        items: [],
        professions: [],
        departments: [],
        editor: null,
        selectedItemIndex: -1
    },
    methods: {
        openEditor: function () {
            var selectedItem = this.items[this.selectedItemIndex];
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
            this.$http.post('/api/employee', this.editor).then(response => {
                this.closeEditor();
                this.refreshItems();
            });
        },
        updateItem: function () {
            this.$http.put(`/api/employee/${this.editor.id}`, this.editor).then(response => {
                this.closeEditor();
                this.refreshItems();
            });
        },
        deleteItem: function () {
            var id = this.items[this.selectedItemIndex].id;
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
})