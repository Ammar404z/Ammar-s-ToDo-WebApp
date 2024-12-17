<script setup lang="ts">
import config from '@/config'
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

    <button type="submit">Create</button>
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

button {
  background-color: #2cec4c;
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
  background-color: #24cd40; /* Darker green on hover */
}
button:active {
  background-color: #04ba22; /* Even darker green when active */
}
</style>
