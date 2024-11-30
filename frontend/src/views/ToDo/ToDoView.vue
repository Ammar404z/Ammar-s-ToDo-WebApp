<script setup lang="ts">
import config from '@/config'
import { showToast, Toast } from '@/ts/toasts'
import { faCheck, faXmark } from '@fortawesome/free-solid-svg-icons'
import { onMounted, ref, type Ref } from 'vue'
import { useRouter } from 'vue-router'

interface ToDo {
  id: number
  title: string
  description: string
  finished: boolean
  assigneeList: { prename: string; name: string }[]
  createdDate: number
  dueDate: number
  finishedDate: number | null
}
const ToDos: Ref<ToDo[]> = ref([])
const router = useRouter()

//Fetch all Todos from the API
function fetchTodos() {
  fetch(`${config.apiBaseUrl}/todos`)
    .then((response) => {
      if (!response.ok) {
        throw new Error('Faild to fetch Todos')
      }
      return response.json()
    })
    .then((data: ToDo[]) => {
      ToDos.value = data
    })
    .catch((error) => {
      showToast(new Toast('Error', error.message, 'error', faXmark, 10))
    })
}

function formatDate(timestamp: number): string {
  const date = new Date(timestamp)
  return date.toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}

async function setFinished(todo: ToDo) {
  todo.finished = !todo.finished // Toggle the finished state
  todo.finishedDate = todo.finished ? Date.now() : null // Update the finished date

  try {
    const response = await fetch(`${config.apiBaseUrl}/todos/${todo.id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(todo)
    })
    if (!response.ok) {
      throw new Error('Failed to update ToDo status')
    }
    showToast(new Toast('Success', 'ToDo status updated successfully', 'success', faCheck, 5))
  } catch (error: any) {
    console.error('Error updating ToDo:', error)
    showToast(new Toast('Error', error.message, 'error', faXmark, 10))
  }
}

function deleteTodo(id: number) {
  if (confirm('Are you sure you want to delete this ToDo?')) {
    fetch(`${config.apiBaseUrl}/todos/${id}`, {
      method: 'DELETE'
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Failed to delete ToDo')
        }
        showToast(new Toast('Success', 'ToDo deleted successfully', 'success', faCheck, 5))
        ToDos.value = ToDos.value.filter((todo) => todo.id !== id)
      })
      .catch((error) => {
        showToast(new Toast('Error', error.message, 'error', faXmark, 10))
      })
  }
}

onMounted(fetchTodos)
</script>

<template>
  <h1>Todos</h1>

  <div v-if="ToDos.length === 0">
    <p>No ToDos found</p>
  </div>
  <div v-else class="ToDoContainer">
    <div v-for="todo in ToDos" :key="todo.id" class="ToDoCard">
      <h3>{{ todo.title }}</h3>
      <p>{{ todo.description }}</p>
      <p>
        Assigned To:
        {{ todo.assigneeList.map((assignee) => assignee.prename + ' ' + assignee.name).join(', ') }}
      </p>
      <p>Due Date: {{ formatDate(todo.dueDate) }}</p>
      <!-- Checkbox to mark the ToDo as finished -->
      <label>
        <input type="checkbox" :checked="todo.finished" @change="setFinished(todo)" />
        Mark as Finished
      </label>
      <!-- Button to delete the ToDo -->
      <button class="delete" @click="deleteTodo(todo.id)">Delete</button>
    </div>
  </div>
</template>

<style scoped>
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
  border: 1px solid #42b983; /* Vue.js green */
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
</style>
