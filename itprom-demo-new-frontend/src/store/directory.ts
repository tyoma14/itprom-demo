import { defineStore } from 'pinia'
import Profession from "@/model/Profession";

export const useDirectoryStore = defineStore('directory', {
  state: () => ({
    professions: [] as Profession[]
  }),
  actions: {
    setProfessions(professions: Profession[]) {
      this.professions = professions
    }
  },
})
