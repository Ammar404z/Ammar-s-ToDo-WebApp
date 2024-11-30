import AssigneesView from '@/views/Assignee/AssigneesView.vue'
import createAssigneeView from '@/views/Assignee/createAssigneeView.vue'
import editAssigneeView from '@/views/Assignee/editAssigneeView.vue'
import Home from '@/views/Home.vue'
import { createRouter, createWebHashHistory } from 'vue-router'

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
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
