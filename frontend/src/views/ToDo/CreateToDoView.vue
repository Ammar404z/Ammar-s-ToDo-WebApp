<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useCreateTodo } from '@/composables/ToDo/UseCreateToDo'

const router = useRouter()
const {
  title,
  description,
  dueDate,
  selectedAssignees,
  availableAssignees,
  fetchAvailableAssignees,
  toggleAssigneeSelection,
  createToDo
} = useCreateTodo()

// Fetch available assignees when mounted
fetchAvailableAssignees()
</script>

<template>
  <h1>Create a New ToDo</h1>

  <form @submit.prevent="createToDo" class="form">
    <div class="form-group">
      <label for="title">Title:</label>
      <input id="title" v-model="title" type="text" required placeholder="Enter ToDo title" />
    </div>

    <div class="form-group">
      <label for="description">Description:</label>
      <textarea
        id="description"
        v-model="description"
        placeholder="Enter ToDo description"
      ></textarea>
    </div>

    <div class="form-group">
      <label for="dueDate">Due Date:</label>
      <input id="dueDate" v-model="dueDate" type="date" />
    </div>

    <div class="form-group">
      <label>Assign To:</label>
      <div class="assignee-list">
        <div v-for="assignee in availableAssignees" :key="assignee.id" class="assignee-item">
          <span>{{ assignee.prename }} {{ assignee.name }}</span>
          <button
            type="button"
            @click="toggleAssigneeSelection(assignee.id)"
            :class="{ selected: selectedAssignees.includes(assignee.id) }"
          >
            {{ selectedAssignees.includes(assignee.id) ? 'Remove' : 'Add' }}
          </button>
        </div>
      </div>
    </div>

    <button class="create-btn">Create</button>
    <button class="cancel-btn" @click="router.push('/todos')">Cancel</button>
  </form>
</template>

<style scoped>
.form {
  display: flex;
  flex-direction: column;
  gap: 20px;
  width: 1000px;
  margin: 0 auto;
  background-color: #ffffff; /* White background */
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3); /* Subtle shadow */
}

.form-group {
  margin-bottom: 20px;
}

label {
  font-size: 1rem;
  font-weight: bold;
  margin-bottom: 5px;
  color: #333333;
}

input,
textarea {
  width: 100%;
  padding: 10px;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.05);
  background-color: white;
}

#dueDate {
  background-color: #00000024;
}

input:focus,
textarea:focus {
  outline: none;
  border-color: #2cec4c;
  box-shadow: 0 0 8px #04ba22;
}

.assignee-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.assignee-item {
  display: flex;
  justify-content: space-between;
  color: black;
  align-items: center;
  background-color: #f8f9fa;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
  transition: background-color 0.3s ease;
}

.assignee-item:hover {
  background-color: #bcffc6;
}

button {
  padding: 5px 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-weight: bold;
  font-size: 0.9rem;
  color: white;
  border: none;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  border-radius: 5px;
  transition: all 0.3s ease; /* Smooth transition for animations */
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.2); /* Subtle shadow */
  margin: 10px 0;
}

button.selected {
  background-color: #ff5555; /* Red for "Remove" */
  color: white;
}

button:hover {
  background-color: #2cec4c;
  color: white;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

button.add {
  background-color: #2cec4c;
  color: white;
}

button.add:hover {
  background-color: #2cec2c;
}

h1 {
  text-align: center;
  font-size: 2rem;
  margin-bottom: 20px;
  color: #42b983;
}

textarea {
  height: 80px;
  resize: none;
}

button.create-btn {
  background-color: #2cec4c;
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

button.create-btn:hover {
  background-color: #24cd40;
  transform: scale(1.05); /* Slight zoom-in effect */
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3); /* Enhanced shadow */
}

button.create-btn:active {
  background-color: #04ba22;
  transform: scale(0.95); /* Slight shrink effect on click */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* Smaller shadow */
}

button.cancel-btn {
  background-color: #ff5555;
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

button.cancel-btn:hover {
  background-color: #cc4444;
  transform: scale(1.05); /* Slight zoom-in effect */
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3); /* Enhanced shadow */
}

button.cancel-btn:active {
  background-color: #a33a3a;
  transform: scale(0.95); /* Slight shrink effect on click */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* Smaller shadow */
}
</style>
