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
        <div class="form-group">
          <label for="prename">Prename:</label>
          <input v-model="assignee.prename" id="prename" type="text" required />
        </div>

        <div class="form-group">
          <label for="name">Name:</label>
          <input v-model="assignee.name" id="name" type="text" required />
        </div>

        <div class="form-group">
          <label for="email">Email:</label>
          <input v-model="assignee.email" id="email" type="email" required />
        </div>

        <button class="save">Save</button>
        <button class="cancel" @click="router.push('/assignees')">Cancel</button>
      </form>
    </div>
  </div>
</template>

<style scoped>
form {
  display: flex;
  flex-direction: column;
  gap: 15px; /* Adds space between each form group */
  max-width: 400px; /* Limit the width for better readability */
  margin: 0 auto; /* Center the form horizontally */
}

.form-group {
  display: flex;
  flex-direction: column; /* Align label and input vertically */
}

label {
  font-weight: bold;
  margin-bottom: 5px; /* Space between label and input */
}

input {
  padding: 10px;
  font-size: 1rem;
  border: 1px solid #2cec4c;
  border-radius: 5px;
  outline: none;
  transition: box-shadow 0.3s ease;
  background-color: white;
}

input:focus {
  box-shadow: 0 0 8px #04ba22;
}

.save {
  background-color: #2cec4c;
  color: white;
  border: none;
  padding: 12px 20px;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  border-radius: 5px;
  transition: all 0.3s ease; /* Smooth transition for animations */
}
.cancel {
  background-color: #e74c3c;
  color: white;
  border: none;
  padding: 12px 20px;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  border-radius: 5px;
  transition: all 0.3s ease; /* Smooth transition for animations */
}

.save:hover {
  background-color: #24cd40; /* Darker green on hover */
  transform: scale(1.05); /* Slight zoom-in effect */
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3); /* Enhanced shadow */
}

.save:active {
  background-color: #04ba22; /* Even darker green when active */
  transform: scale(0.95); /* Slight shrink effect on click */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* Smaller shadow */
}
.cancel:hover {
  background-color: #cc4444; /* Darker red on hover */
  transform: scale(1.05); /* Slight zoom-in effect */
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3); /* Enhanced shadow */
}

.cancel:active {
  background-color: #a33a3a; /* Even darker red when active */
  transform: scale(0.95); /* Slight shrink effect on click */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* Smaller shadow */
}

.error {
  color: #e74c3c;
  font-size: 14px;
  margin-top: -10px;
}
</style>
