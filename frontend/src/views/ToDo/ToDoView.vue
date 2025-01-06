<script setup lang="ts">
import config from '@/config'
import { showToast, Toast } from '@/ts/toasts'
import { faCheck, faXmark } from '@fortawesome/free-solid-svg-icons'
import { computed, onMounted, ref, type Ref } from 'vue'
import { useRouter } from 'vue-router'
import { useToDo } from '@/composables/ToDo/UseToDo'

const {
  showFinished,
  router,
  searchQuery,
  currentSort,
  filteredUnfinishedToDos,
  filteredFinishedToDos,
  fetchTodos,
  formatDate,
  setFinished,
  deleteTodo,
  downloadCsv
} = useToDo()

onMounted(fetchTodos)
</script>

<template>
  <div class="header-container">
    <h1>ToDos</h1>
    <div class="header-container-buttons">
      <button class="create-button" @click="router.push('/create-todo')">+ Create ToDo</button>
      <button class="download-button" @click="downloadCsv()">Download CSV</button>
    </div>
  </div>

  <!-- Filter -->
  <div class="filter-container">
    <label for="filter">Filter by Title:</label>
    <input id="filter" v-model="searchQuery" type="text" placeholder="Enter title to filter" />
  </div>

  <!-- Sorting -->
  <div class="sorting-container">
    <label for="sort">Sort Todos:</label>
    <select id="sort" v-model="currentSort">
      <option value="titleAscending">Title (A-Z)</option>
      <option value="titleDescending">Title (Z-A)</option>
      <option value="dueDateAscending">Due Date (Earliest First)</option>
      <option value="dueDateDescending">Due Date (Latest First)</option>
    </select>
  </div>

  <!-- Unfinished Todos -->
  <h2>Unfinished Todos</h2>
  <div v-if="filteredUnfinishedToDos.length === 0">No unfinished Todos</div>
  <div v-else class="ToDoContainer">
    <div v-for="todo in filteredUnfinishedToDos" :key="todo.id" class="ToDoCard">
      <h3>{{ todo.title }}</h3>
      <p>Description: {{ todo.description }}</p>
      <p>Assigned To: {{ todo.assigneeList.map((a) => a.prename + ' ' + a.name).join(', ') }}</p>
      <p>Due Date: {{ formatDate(todo.dueDate) }}</p>
      <p>Category: {{ todo.category }}</p>
      <label>
        <input type="checkbox" :checked="todo.finished" @change="setFinished(todo)" />
        Mark as Finished
      </label>
      <button class="edit" @click="router.push(`/todos/${todo.id}`)">Edit</button>
      <button class="delete" @click="deleteTodo(todo.id)">Delete</button>
    </div>
  </div>

  <!-- Finished Todos -->
  <h2>
    <button class="edit" @click="showFinished = !showFinished">
      {{ showFinished ? 'Hide Finished Todos' : 'Show Finished Todos' }}
    </button>
  </h2>
  <div v-if="showFinished">
    <div v-if="filteredFinishedToDos.length === 0">No finished Todos</div>
    <div v-else class="ToDoContainer">
      <div v-for="todo in filteredFinishedToDos" :key="todo.id" class="ToDoCard">
        <h3>{{ todo.title }}</h3>
        <p>{{ todo.description }}</p>
        <p>Assigned To: {{ todo.assigneeList.map((a) => a.prename + ' ' + a.name).join(', ') }}</p>
        <p>Due Date: {{ formatDate(todo.dueDate) }}</p>
        <p>Finished Date: {{ formatDate(todo.finishedDate!) }}</p>
        <p>Category: {{ todo.category }}</p>
        <label>
          <input type="checkbox" :checked="todo.finished" @change="setFinished(todo)" />
          Mark as Unfinished
        </label>
        <button class="edit" @click="router.push(`/todos/${todo.id}`)">Edit</button>
        <button class="delete" @click="deleteTodo(todo.id)">Delete</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.header-container {
  display: flex;
  justify-content: space-between; /* Place content on opposite ends */
  align-items: center; /* Align items vertically */
  margin-bottom: 20px;
}

.header-container h1 {
  margin: 0; /* Reset margin to ensure proper alignment */
}

.header-container-buttons {
  display: flex;
  flex-direction: column; /* Stack buttons vertically */
  align-items: flex-end; /* Align buttons to the right */
  gap: 10px; /* Add spacing between the buttons */
}

.create-button {
  background-color: #2cec4c;
  color: white;
  border: none;
  padding: 10px 15px;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  border-radius: 5px;
  transition: all 0.3s ease;
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.2);
}

.create-button:hover {
  background-color: #24cd40; /* Slightly darker green on hover */
  transform: scale(1.05); /* Slight zoom-in */
}

.create-button:active {
  background-color: #04ba22;
  transform: scale(0.95);
}

.download-button {
  background-color: #db8c0d;
  color: white;
  border: none;
  padding: 10px 15px;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  border-radius: 5px;
  transition: all 0.3s ease;
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.2);
}

.download-button:hover {
  background-color: #e99309;
  transform: scale(1.05); /* Slight zoom-in */
}

.download-button:active {
  background-color: #bc7503;
  transform: scale(0.95);
}

/* Styling for the filter (search input) */
.filter-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 20px 0;
}

.filter-container label {
  font-size: 1.2rem;
  color: #333333;
  margin-bottom: 10px;
}

.filter-container input {
  width: 20%;
  padding: 10px 15px;
  font-size: 1rem;
  border: 1px solid #2cec4c;
  border-radius: 5px;
  outline: none;
  transition: box-shadow 0.3s ease;
  background-color: white;
  color: black;
}

.filter-container input:focus {
  box-shadow: 0 0 8px #04ba22;
}

/* Styling for the sorting dropdown */
.sorting-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 20px 0;
}

.sorting-container label {
  font-size: 1.2rem;
  color: #333333;
  margin-bottom: 10px;
}

.sorting-container select {
  width: 20%;
  padding: 10px 15px;
  font-size: 1rem;
  border: 1px solid #2cec4c;
  border-radius: 5px;
  outline: none;
  background-color: #ffffff;
  transition:
    box-shadow 0.3s ease,
    background-color 0.3s ease;
}

.sorting-container select:hover {
  background-color: #f8f8f8;
}

.sorting-container select:focus {
  box-shadow: 0 0 8px #04ba22;
}
.ToDoContainer {
  display: flex;
  flex-wrap: wrap; /* Allows wrapping to the next line if cards don't fit */
  justify-content: center; /* Centers the cards horizontally */
  gap: 20px; /* Adds spacing between the cards */
  padding: 20px;
}

.ToDoCard {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px;
  margin: 15px;
  border: 1px solid #2cec4c;
  border-radius: 10px;
  background-color: #ffffff; /* Bright white for better contrast */
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.1); /* Subtle shadow for depth */
  width: 300px;
  text-align: center;
  transition:
    transform 0.2s ease-in-out,
    box-shadow 0.2s ease-in-out;
}

.ToDoCard h3 {
  color: #000000; /* Black text for the name */
  font-size: 1.5rem;
  margin-bottom: 10px;
}

.ToDoCard:hover {
  transform: scale(1.05); /* Slight zoom-in effect */
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.2); /* More pronounced shadow */
}

.ToDoCard label {
  display: flex;
  align-items: center;
  font-size: 16px;
  color: #333333; /* Dark text for better contrast */
  margin-top: 10px;
}

.ToDoCard input[type='checkbox'] {
  margin-right: 10px;
  transform: scale(1.2); /* Slightly larger checkbox */
  cursor: pointer;
}

p {
  color: #555555; /* Medium gray for better readability */
  font-size: 16px;
  margin: 5px 0;
}

h1 {
  font-size: 2.5rem;
  color: #333333; /* Dark text for better contrast */
  margin-bottom: 25px;
}
button.delete {
  background-color: #ff5555; /* Red color */
  color: white;
  border: none;
  padding: 12px 20px;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  border-radius: 5px;
  transition: all 0.3s ease; /* Smooth transition for animations */
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.2); /* Subtle shadow */
  margin: 10px 0;
}

button.delete:hover {
  background-color: #cc4444; /* Darker red on hover */
  transform: scale(1.05); /* Slight zoom-in effect */
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3); /* Enhanced shadow */
}

button.delete:active {
  background-color: #a33a3a; /* Even darker red when active */
  transform: scale(0.95); /* Slight shrink effect on click */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* Smaller shadow */
}

button.edit {
  background-color: #db8c0d;
  color: white;
  border: none;
  padding: 12px 20px;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  border-radius: 5px;
  transition: all 0.3s ease; /* Smooth transition for animations */
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.2); /* Subtle shadow */
  margin: 10px 0;
}

button.edit:hover {
  background-color: #e99309;
  transform: scale(1.05); /* Slight zoom-in effect */
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3); /* Enhanced shadow */
}

button.edit:active {
  background-color: #bc7503;
  transform: scale(0.95); /* Slight shrink effect on click */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* Smaller shadow */
}
</style>
