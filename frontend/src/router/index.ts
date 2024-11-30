import AboutView from '@/views/AboutView.vue'
import AssigneesView from '@/views/AssigneesView.vue'
import CatsView from '@/views/CatsView.vue'
import createAssigneeView from '@/views/createAssigneeView.vue'
import editAssigneeView from '@/views/editAssigneeView.vue'
import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/about',
      name: 'about',
      component: AboutView
    },
    {
      path: '/cats',
      name: 'cats',
      component: CatsView
    },
    {
      path: '/assignees',
      name: 'assignees',
      component: AssigneesView
    },
    {
      path: '/create-assignee',
      name: 'create-assignee',
      component: createAssigneeView
    },
    {
      path: '/assignees/:id',
      name: 'edit-assignee',
      component: editAssigneeView
    }
  ]
})

export default router
