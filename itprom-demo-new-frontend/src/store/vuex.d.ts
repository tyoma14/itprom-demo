import { Store } from 'vuex'
import Profession from "@/model/Profession";

declare module '@vue/runtime-core' {
    // declare your own store states
    interface State {
        professions: Profession[]
    }

    // provide typings for `this.$store`
    interface ComponentCustomProperties {
        $store: Store<State>
    }
}