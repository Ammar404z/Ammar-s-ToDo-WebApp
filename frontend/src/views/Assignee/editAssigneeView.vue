<script setup lang="ts">
import config from '@/config'
import { showToast, Toast } from '@/ts/toasts'
import { faCheck, faXmark } from '@fortawesome/free-solid-svg-icons'
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'

// Interface for Assignee
interface Assignee {
  id: number
  prename: string
  name: string
  email: string
}

// State variables
const route = useRoute()
const router = useRouter()
const assignee = ref<Assignee | null>(null)
const loading = ref(true)

// Fetch the assignee by ID
const fetchAssigneeById = async (id: number) => {
  try {
    const response = await fetch(`${config.apiBaseUrl}/assignees/${id}`)
    if (!response.ok) {
      throw new Error(`Failed to fetch assignee with ID ${id}: ${response.statusText}`)
    }
    const fetchedAssignee = await response.json()
    if (fetchedAssignee) {
      assignee.value = fetchedAssignee
    } else {
      throw new Error('Assignee not found')
    }
  } catch (error: any) {
    console.error('Error fetching assignee:', error)
    showToast(new Toast('Error', error.message, 'error', faXmark))
  } finally {
    loading.value = false
  }
}
// Update the assignee
const updateAssignee = async () => {
  if (!assignee.value) return

  try {
    const response = await fetch(`${config.apiBaseUrl}/assignees/${assignee.value.id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(assignee.value)
    })
    if (!response.ok) {
      throw new Error('Failed to update assignee')
    }
    showToast(new Toast('Success', `Assignee updated successfully`, 'success', faCheck))
    router.push('/assignees') // Redirect to the assignees list after success
  } catch (error: any) {
    showToast(new Toast('Error', error.message, 'error', faXmark))
  }
}

// Load the assignee on mount
onMounted(() => {
  const id = parseInt(route.params.id as string, 10)
  console.log('Route parameter ID:', id) // Debugging log
  if (!isNaN(id)) {
    fetchAssigneeById(id)
  } else {
    // If the ID is invalid, only show the toast and stop further actions
    showToast(new Toast('Error', 'Invalid or missing assignee ID', 'error', faXmark))
  }
})
</script>

<template>
  <div>
    <h1>Edit Assignee</h1>

    <div v-if="loading">Loading assignee...</div>
    <div v-else>
      <form @submit.prevent="updateAssignee">
        <div>
          <label for="prename">Prename:</label>
          <input v-model="assignee.prename" id="prename" type="text" required />
        </div>

        <div>
          <label for="name">Name:</label>
          <input v-model="assignee.name" id="name" type="text" required />
        </div>

        <div>
          <label for="email">Email:</label>
          <input v-model="assignee.email" id="email" type="email" required />
        </div>

        <button type="submit" :disabled="loading">Save</button>
        <button type="button" @click="router.push('/assignees')">Cancel</button>
      </form>
    </div>
  </div>
</template>

<style scoped>
form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 10px;
  background-color: #ffffff;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

h1 {
  font-size: 2rem;
  text-align: center;
  color: #333;
  margin-bottom: 20px;
}

label {
  font-weight: bold;
  font-size: 14px;
  color: #555;
  margin-bottom: 5px;
}

input {
  padding: 10px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 5px;
  transition: border-color 0.3s ease;
}

input:focus {
  outline: none;
  border-color: #42b983;
}

button {
  padding: 10px 15px;
  font-size: 16px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  background-color: #42b983;
  color: white;
  font-weight: bold;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #359670;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

button.cancel {
  background-color: #e74c3c;
}

button.cancel:hover {
  background-color: #c0392b;
}

.error {
  color: #e74c3c;
  font-size: 14px;
  margin-top: -10px;
}
</style>
