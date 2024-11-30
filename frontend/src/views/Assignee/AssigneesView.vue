<script setup lang="ts">
import config from '@/config'
import { showToast, Toast } from '@/ts/toasts'
import { faCheck, faXmark } from '@fortawesome/free-solid-svg-icons'
import { onMounted, ref, type Ref } from 'vue'
import { useRouter } from 'vue-router'

interface Assignee {
  id: number
  prename: string
  name: string
  email: string
}

const assignees: Ref<Assignee[]> = ref([])
const router = useRouter()

// Fetch all assignees
function fetchAllAssignees() {
  fetch(`${config.apiBaseUrl}/assignees`)
    .then((response) => {
      if (!response.ok) {
        throw new Error('Failed to fetch assignees')
      }
      return response.json()
    })
    .then((data: Assignee[]) => {
      assignees.value = data
    })
    .catch((error) => {
      console.error('Error fetching assignees:', error)
      showToast(new Toast('Error', error.message, 'error', faXmark, 10))
    })
}

// Delete an assignee
function deleteAssignee(id: number) {
  if (confirm('Are you sure you want to delete this assignee?')) {
    fetch(`${config.apiBaseUrl}/assignees/${id}`, {
      method: 'DELETE'
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Failed to delete assignee')
        }
        showToast(new Toast('Success', 'Assignee deleted successfully', 'success', faCheck))
        assignees.value = assignees.value.filter((assignee) => assignee.id !== id)
      })
      .catch((error) => {
        showToast(new Toast('Error', error.message, 'error', faXmark))
      })
  }
}

onMounted(fetchAllAssignees)
</script>

<template>
  <h1>Assignees</h1>

  <div v-if="assignees.length === 0">
    <p>No assignees found.</p>
  </div>
  <div v-else class="assigneeContainer">
    <div v-for="assignee in assignees" :key="assignee.id" class="assigneeCard">
      <h3>{{ assignee.prename }} {{ assignee.name }}</h3>
      <p>{{ assignee.email }}</p>
      <button @click="router.push(`/assignees/${assignee.id}`)">Edit</button>
      <button @click="deleteAssignee(assignee.id)">Delete</button>
    </div>
  </div>
</template>

<style scoped>
.assigneeContainer {
  display: flex;
  flex-wrap: wrap; /* Allows wrapping to the next line if cards don't fit */
  justify-content: center; /* Centers the cards horizontally */
  gap: 20px; /* Adds spacing between the cards */
  padding: 20px;
}

.assigneeCard {
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

.assigneeCard h3 {
  color: #000000; /* Black text for the name */
  font-size: 1.5rem;
  margin-bottom: 10px;
}

.assigneeCard:hover {
  transform: scale(1.05); /* Slight zoom-in effect */
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.2); /* More pronounced shadow */
}

button {
  background-color: #42b983; /* Vue.js green */
  color: white;
  border: none;
  padding: 12px 20px;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  border-radius: 5px;
  margin-top: 10px;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #359670; /* Darker green on hover */
}

button:active {
  background-color: #2c7b60; /* Even darker green when active */
}

h1 {
  font-size: 2.5rem;
  color: #333333; /* Dark text for better contrast */
  margin-bottom: 25px;
}

p {
  color: #555555; /* Medium gray for better readability */
  font-size: 16px;
  margin: 5px 0;
}
</style>
