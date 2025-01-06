<script setup lang="ts">
/**
 * The EditAssigneeView component allows users to edit the details of an existing assignee.
 * It provides fields for editing the first name, last name, and email of the selected assignee.
 */
import { useAssignee } from '@/composables/Assignees/UseEditAssignee'
import { showToast, Toast } from '@/ts/toasts'
import { faXmark } from '@fortawesome/free-solid-svg-icons'
import { onMounted } from 'vue'
import { useRoute } from 'vue-router'

// Reactive properties and methods for assignee management
const route = useRoute()
const { assignee, loading, fetchAssigneeById, updateAssignee } = useAssignee()

/**
 * Fetch the assignee details based on the route parameter when the component is mounted.
 */
onMounted(() => {
  const id = parseInt(route.params.id as string, 10)
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
