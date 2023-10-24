import {createRouter, createWebHashHistory, RouteRecordRaw} from 'vue-router'
import ProfessionsView from "@/views/ProfessionsView.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'professions',
    component: ProfessionsView
  },
  {
    path: '/professions',
    name: 'professions',
    component: ProfessionsView
  },
  // {
  //   path: '/departments',
  //   name: 'departments',
  //   component: DepartmentsView
  // },
  // {
  //   path: '/employees',
  //   name: 'employees',
  //   component: EmployeesView
  // }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
