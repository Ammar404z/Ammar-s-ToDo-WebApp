<script setup lang="ts">
import { useCreateAssignee } from '@/composables/Assignees/useCreateAssignee'

const { newPrename, newName, newEmail, handleSubmit, cancel } = useCreateAssignee()
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
