<script setup lang="ts">
import config from '@/config'
import router from '@/router'
import { showToast, Toast } from '@/ts/toasts'
import { faCheck, faXmark } from '@fortawesome/free-solid-svg-icons'
import { ref } from 'vue'

interface Assignee {
  id: number
  prename: string
  name: string
  email: string
}

const newPrename = ref('')
const newName = ref('')
const newEmail = ref('')

function createAssignee(prename: string, name: string, email: string) {
  fetch(`${config.apiBaseUrl}/assignees`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ prename, name, email })
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error('Failed to create assignee')
      }
      return response.json()
    })
    .then(() => {
      showToast(new Toast('Success', `Assignee ${name} created successfully`, 'success', faCheck))
    })
    .catch((error) => {
      showToast(new Toast('Error', error.message, 'error', faXmark))
    })
}

function handleSubmit() {
  createAssignee(newPrename.value, newName.value, newEmail.value)
  newPrename.value = ''
  newName.value = ''
  newEmail.value = ''
  router.push('/assignees')
}
function cancel() {
  router.push('/assignees')
}
</script>

<template>
  <h1>Create Assignees</h1>
  <form @submit.prevent="handleSubmit" class="form-container">
    <label for="prename">Prename:</label>
    <input v-model="newPrename" id="prename" type="text" required />

    <label for="name">Name:</label>
    <input v-model="newName" id="name" type="text" required />

    <label for="email">Email:</label>
    <input v-model="newEmail" id="email" type="email" required />

    <button class="save">Create</button>
    <button class="cancel" @click="cancel">Cancel</button>
  </form>
</template>

<style scoped>
/* Styling for vertically aligned form */
.form-container {
  display: flex;
  flex-direction: column;
  gap: 10px; /* Space between elements */
  max-width: 400px;
  margin: 0 auto; /* Center the form */
}

.form-container input {
  padding: 10px 15px;
  font-size: 1rem;
  border: 1px solid #2cec4c;
  border-radius: 5px;
  outline: none;
  transition: box-shadow 0.3s ease;
  background-color: white;
  color: black;
}

form label {
  font-weight: bold;
  margin-bottom: 5px;
}

form input {
  background-color: white;
  padding: 8px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
  color: black;
  border: 1px solid #2cec4c;
}
form input:focus {
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
</style>
