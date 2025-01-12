<template>
  <h1>Welcome to Ammar's ToDo Web Application</h1>

  <div class="button-container">
    <!-- Row for Todos -->
    <div class="button-row">
      <button class="nav-button" @click="navigateTo('todos')">Todos</button>
      <button class="nav-button create" @click="navigateTo('create-todo')">+ Create Todos</button>
    </div>

    <!-- Row for Assignees -->
    <div class="button-row">
      <button class="nav-button" @click="navigateTo('assignees')">Assignees</button>
      <button class="nav-button create" @click="navigateTo('create-assignee')">
        + Create Assignees
      </button>
    </div>
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
/* Center the main container */
h1 {
  text-align: center;
  color: #2c3e50; /* Deep indigo for a professional touch */
  margin-bottom: 30px;
  font-size: 2rem;
  font-weight: bold;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.2); /* Subtle shadow for depth */
}

/* Button container layout */
.button-container {
  display: flex;
  flex-wrap: wrap; /* Wrap buttons for responsiveness */
  justify-content: center;
  gap: 20px; /* More space between buttons */
  margin-top: 40px; /* Better spacing from the title */
  padding: 10px;
}

/* Base button styles */
.nav-button {
  padding: 15px 30px;
  font-size: 1rem;
  font-weight: bold;
  color: white;
  background-color: #2cec4c; /* Vibrant green */
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition:
    background-color 0.3s ease,
    transform 0.2s ease,
    box-shadow 0.3s ease;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Subtle shadow for depth */
  text-transform: uppercase; /* Modern look */
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

/* Hover and active states */
.nav-button:hover {
  background-color: #24cd40; /* Slightly darker green */
  transform: scale(1.08); /* Larger hover zoom */
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2); /* Enhanced shadow */
}

.nav-button:active {
  background-color: #04ba22; /* Darker green on click */
  transform: scale(0.95); /* Slight shrink effect on click */
}

/* Focus state for accessibility */
.nav-button:focus {
  outline: 3px solid #2c8d65; /* Bright green outline */
  outline-offset: 2px;
}

/* Media queries for responsiveness */
@media (max-width: 768px) {
  h1 {
    font-size: 1.5rem; /* Adjust title size for smaller screens */
  }

  .nav-button {
    width: 100%; /* Full-width buttons on smaller devices */
    text-align: center;
  }
}
.nav-button.create {
  background-color: #218f37; /* Darker green shade */
  color: white;
}

.nav-button.create:hover {
  background-color: #1a7230; /* Even darker green on hover */
  transform: scale(1.08); /* Slight zoom effect */
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2); /* Enhanced shadow */
}

.nav-button.create:active {
  background-color: #145b26; /* Darkest green on click */
  transform: scale(0.95); /* Slight shrink effect on click */
}
.button-container {
  display: flex;
  flex-direction: column; /* Stack rows vertically */
  align-items: center;
  gap: 20px; /* Space between rows */
}

/* Style rows */
.button-row {
  display: flex;
  gap: 20px; /* Space between buttons in a row */
}
</style>
