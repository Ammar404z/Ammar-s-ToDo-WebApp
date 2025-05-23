import AssigneesView from '@/views/Assignee/AssigneesView.vue'
import createAssigneeView from '@/views/Assignee/CreateAssigneeView.vue'
import editAssigneeView from '@/views/Assignee/EditAssigneeView.vue'
import Home from '@/views/Home.vue'
import CreateToDoView from '@/views/ToDo/CreateToDoView.vue'
import editToDosView from '@/views/ToDo/EditToDosView.vue'
import ToDoView from '@/views/ToDo/ToDoView.vue'
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
    },
    {
      path: '/todos',
      name: 'todos',
      component: ToDoView
    },
    {
      path: '/create-todo',
      name: 'create-todo',
      component: CreateToDoView
    },
    {
      path: '/todos/:id',
      name: 'edit-todos',
      component: editToDosView
    }
  ]
})

export default router
