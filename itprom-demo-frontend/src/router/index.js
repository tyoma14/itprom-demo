import Vue from 'vue'
import VueRouter from 'vue-router'
import ProfessionsView from "@/views/ProfessionsView.vue";
import DepartmentsView from "@/views/DepartmentsView.vue";
import EmployeesView from "@/views/EmployeesView.vue";

Vue.use(VueRouter)

const routes = [
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
  {
    path: '/departments',
    name: 'departments',
    component: DepartmentsView
  },
  {
    path: '/employees',
    name: 'employees',
    component: EmployeesView
  }
]

const router = new VueRouter({
  routes
})

export default router
