export default class Profession {

    id: number | undefined;
    name: string | undefined;
    note: string | undefined;
    selected: boolean

    constructor(item?: Profession) {
        this.id = undefined
        this.name = undefined
        this.note = undefined
        this.selected = false
        this.setValues(item)
    }

    setValues(item?: Profession) {
        if (item) {
            this.id = item.id
            this.name = item.name
            this.note = item.note
            this.selected = item.selected
        }
    }

}