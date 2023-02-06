var app = new Vue({
    el: '#app',
    data: {
        professions: [],
        editor: null,
        selectedItemIndex: -1
    },
    methods: {
        openEditor: function () {
            var selectedProfession = this.professions[this.selectedItemIndex];
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
            this.$http.post('/api/profession', this.editor).then(response => {
                this.closeEditor();
                this.refreshProfessions();
            });
        },
        updateProfession: function () {
            this.$http.put(`/api/profession/${this.editor.id}`, this.editor).then(response => {
                this.closeEditor();
                this.refreshProfessions();
            });
        },
        deleteProfession: function () {
            var id = this.professions[this.selectedItemIndex].id;
            this.$http.delete(`/api/profession/${id}`).then(() => {
                this.refreshProfessions();
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
})