<script setup lang="ts">
import { activeToasts } from '@/ts/toasts'
import { Close, Toast, Toasts } from 'agnostic-vue'
import { RouterView } from 'vue-router'

import 'agnostic-vue/dist/common.min.css'
import 'agnostic-vue/dist/index.css' /* PartiallyEnd: #3632/scriptSetup.vue */
import 'agnostic-vue/dist/index.css'
</script>

<template>
  <div id="app">
    <header>
      <nav>
        <RouterLink to="/">Home</RouterLink>
        <RouterLink to="/assignees">Assignees</RouterLink>
        <RouterLink to="/todos">ToDos</RouterLink>
      </nav>
    </header>

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
/* Main Container for the App */
.main {
  padding: 10px 20px;
}

/* Header Styles */
header {
  background-color: #000000;
  padding: 15px 30px;
  display: flex;
  justify-content: space-between; /* Space between title and navigation */
  align-items: center;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* Subtle shadow */
  transition:
    background-color 0.3s ease,
    box-shadow 0.3s ease;
}

header:hover {
  background-color: #1e1e1e;
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.15); /* Enhanced shadow */
}

/* Header Title */
header h1 {
  font-size: 2rem;
  color: #ffffff;
  margin: 0;
  font-weight: bold;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.3);
  transition:
    transform 0.3s ease,
    color 0.3s ease;
}

header h1:hover {
  transform: scale(1.05); /* Slight zoom effect */
  color: #2cec4c; /* Bright green */
}

/* Navigation Styles */
header nav {
  display: flex;
  gap: 20px; /* Space between links */
}

header a {
  color: #ffffff;
  text-decoration: none;
  font-size: 1.1rem;
  font-weight: bold;
  padding: 10px 15px;
  border-radius: 5px;
  transition:
    background-color 0.3s ease,
    color 0.3s ease,
    transform 0.2s ease;
}

header a:hover {
  background-color: #2cec4c; /* Bright green */
  color: #2c3e50; /* Vue.js Indigo for contrast */
  transform: translateY(-3px); /* Lift effect */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* Subtle shadow on hover */
}

header a:active {
  transform: translateY(2px); /* Pressed effect */
  background-color: #27ae60; /* Slightly darker green */
}

/* Responsive Design */
@media (max-width: 768px) {
  header {
    flex-direction: column;
    align-items: flex-start;
  }

  header nav {
    flex-direction: column;
    gap: 10px;
    align-items: flex-start;
  }
}
</style>
