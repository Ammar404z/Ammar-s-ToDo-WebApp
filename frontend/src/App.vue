<script setup lang="ts">
import { activeToasts } from '@/ts/toasts'
import { Close, Header, HeaderNavItem, Toast, Toasts } from 'agnostic-vue'
import { RouterView } from 'vue-router'

import 'agnostic-vue/dist/common.min.css'
import 'agnostic-vue/dist/index.css'
</script>

<template>
  <div id="app">
    <Header isHeaderContentStart>
      <template v-slot:headernav>
        <HeaderNavItem>
          <RouterLink to="/">Home</RouterLink>
        </HeaderNavItem>
        <HeaderNavItem>
          <RouterLink to="/assignees">Assignees</RouterLink>
        </HeaderNavItem>
        <HeaderNavItem>
          <RouterLink to="/create-assignee">Create an Assignee</RouterLink>
        </HeaderNavItem>
        <HeaderNavItem>
          <RouterLink to="/todos">ToDos</RouterLink>
        </HeaderNavItem>
        <HeaderNavItem>
          <RouterLink to="/create-todo">Create a ToDo</RouterLink>
        </HeaderNavItem>
      </template>
    </Header>

    <div class="main">
      <RouterView />
    </div>
  </div>

  <Toasts vertical-position="top" horizontal-position="end">
    <template v-for="toast of activeToasts" :key="toast.key">
      <Toast :type="toast.type" class="alert alert-border-left alert-info">
        <div class="flex-fill flex flex-column">
          <div class="flex">
            <h3 class="flex-fill">{{ toast.title }}</h3>
            <Close @click="toast.close()" />
          </div>
          <div class="flex">
            <font-awesome-icon :icon="toast.icon" size="xl" class="mie8 pbs2"></font-awesome-icon>
            <div class="flex-fill">
              {{ toast.message }}
            </div>
          </div>
        </div>
      </Toast>
      <div class="mbe14" />
    </template>
  </Toasts>
</template>

<style scoped>
.main {
  padding: 10px 20px;
}
</style>
