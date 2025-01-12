<template>
  <h1>Welcome to Ammar's ToDo Web Application</h1>

  <div class="button-container">
    <button class="nav-button" @click="navigateTo('todos')">Todos</button>
    <button class="nav-button" @click="navigateTo('create-todo')">+ Create Todos</button>
    <button class="nav-button" @click="navigateTo('assignees')">Assignees</button>
    <button class="nav-button" @click="navigateTo('create-assignee')">+ Create Assignees</button>
  </div>
</template>

<script setup lang="ts">
import router from '@/router'

// navigation Logic
function navigateTo(route: string) {
  router.push(`/${route}`)
}
</script>

<style scoped>
h1 {
  text-align: center;
  color: #2cec4c;
  margin-bottom: 20px;
}

.button-container {
  display: flex;
  justify-content: center;
  gap: 15px;
  margin-top: 20px;
}

.nav-button {
  padding: 10px 20px;
  font-size: 16px;
  font-weight: bold;
  color: white;
  background-color: #2cec4c;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition:
    background-color 0.3s,
    transform 0.2s;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.nav-button:hover {
  background-color: #24cd40; /* Darker green on hover */
  transform: scale(1.05); /* Slight zoom-in effect */
}

.nav-button:active {
  background-color: #04ba22; /* Even darker green on click */
  transform: scale(0.95); /* Slight shrink effect on click */
}

.nav-button:focus {
  outline: 2px solid #2c8d65; /* Focus outline */
}
</style>
