import {createStore} from 'vuex'
import Profession from "@/model/Profession";

interface State {
  professions: Profession[]
}

export default createStore<State>({
  state: {
    professions: []
  },
  getters: {
    getProfessions(state): Profession[] {
      return state.professions
    }
  },
  mutations: {
    setProfessionsMutation(state, professions) {
      state.professions.splice(0)
      Array.prototype.push.apply(state.professions, professions)
    }
  },
  actions: {
    setProfessions(context, professions) {
      context.commit('setProfessionsMutation', professions)
    }
  },
  modules: {
  }
})
