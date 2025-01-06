<script setup lang="ts">
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAssignees } from '@/composables/Assignees/UseAssignees'

const { assignees, fetchAllAssignees, deleteAssignee } = useAssignees()
const router = useRouter()

onMounted(fetchAllAssignees)
</script>

<template>
  <h1>Assignees</h1>

  <div class="action-container">
    <button class="create-assignee-btn" @click="router.push('/create-assignee')">
      + Create New Assignee
    </button>
  </div>

  <div v-if="assignees.length === 0">
    <p>No assignees found.</p>
  </div>
  <div v-else class="assigneeContainer">
    <div v-for="assignee in assignees" :key="assignee.id" class="assigneeCard">
      <h3>{{ assignee.prename }} {{ assignee.name }}</h3>
      <p>{{ assignee.email }}</p>
      <button class="edit-btn" @click="router.push(`/assignees/${assignee.id}`)">Edit</button>
      <button class="delete-btn" @click="deleteAssignee(assignee.id)">Delete</button>
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

.assigneeCard h3 {
  color: #000000; /* Black text for the name */
  font-size: 1.5rem;
  margin-bottom: 10px;
}

.assigneeCard:hover {
  transform: scale(1.05); /* Slight zoom-in effect */
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.2); /* More pronounced shadow */
}
.action-container {
  display: flex;
  justify-content: center; /* Center the button horizontally */
  margin-bottom: 20px;
}

.create-assignee-btn {
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

.create-assignee-btn:hover {
  background-color: #24cd40; /* Darker green on hover */
  transform: scale(1.05); /* Slight zoom-in effect */
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3); /* Enhanced shadow */
}

.create-assignee-btn:active {
  background-color: #04ba22; /* Even darker green when active */
  transform: scale(0.95); /* Slight shrink effect on click */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* Smaller shadow */
}

button.delete-btn {
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

button.delete-btn:hover {
  background-color: #cc4444; /* Darker red on hover */
  transform: scale(1.05); /* Slight zoom-in effect */
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3); /* Enhanced shadow */
}

button.delete-btn:active {
  background-color: #a33a3a; /* Even darker red when active */
  transform: scale(0.95); /* Slight shrink effect on click */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* Smaller shadow */
}

button.edit-btn {
  background-color: #db8c0d; /* Red color */
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

button.edit-btn:hover {
  background-color: #e99309;
  transform: scale(1.05); /* Slight zoom-in effect */
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3); /* Enhanced shadow */
}

button.edit-btn:active {
  background-color: #bc7503;
  transform: scale(0.95); /* Slight shrink effect on click */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* Smaller shadow */
}

h1 {
  font-size: 2.5rem;
  color: #515050; /* Dark text for better contrast */
  margin-bottom: 25px;
}

p {
  color: #555555; /* Medium gray for better readability */
  font-size: 16px;
  margin: 5px 0;
}
</style>
