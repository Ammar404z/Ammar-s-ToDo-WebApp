<script setup lang="ts">
/**
 * The EditToDosView component allows users to edit the details of an existing ToDo item.
 * It provides fields for editing the title, description, due date, and assignees.
 */
import { useEditToDo } from '@/composables/ToDo/UseEditToDo'
import { showToast, Toast } from '@/ts/toasts'
import { faXmark } from '@fortawesome/free-solid-svg-icons'
import { onMounted } from 'vue'

// Reactive properties and methods for ToDo management
const {
  route,
  router,
  toDo,
  allAssignees,
  fetchToDoById,
  fetchAllAssignees,
  toggleAssignee,
  updateToDo
} = useEditToDo()

/**
 * Fetch the ToDo item and available assignees when the component is mounted.
 */
onMounted(() => {
  const id = parseInt(route.params.id as string, 10)
  if (!isNaN(id)) {
    fetchToDoById(id)
    fetchAllAssignees()
  } else {
    showToast(new Toast('Error', 'Invalid or missing ToDo ID', 'error', faXmark))
    router.push('/todos')
  }
})
</script>

<template>
  <div>
    <h1>Edit ToDo</h1>

    <form v-if="toDo" @submit.prevent="updateToDo">
      <div>
        <label for="title">Title:</label>
        <input v-model="toDo.title" id="title" type="text" required />
      </div>

      <div>
        <label for="description">Description:</label>
        <textarea v-model="toDo.description" id="description"></textarea>
      </div>

      <div>
        <label>Assignees:</label>
        <div class="assignee-list">
          <div v-for="assignee in allAssignees" :key="assignee.id" class="assignee-item">
            <span>{{ assignee.prename }} {{ assignee.name }}</span>
            <button
              type="button"
              @click="toggleAssignee(assignee.id)"
              :class="{ selected: toDo.assigneeList.some((a) => a.id === assignee.id) }"
            >
              {{ toDo.assigneeList.some((a) => a.id === assignee.id) ? 'Remove' : 'Add' }}
            </button>
          </div>
          <div>
            <label for="dueDate">Due Date:</label>
            <input v-model="toDo.dueDate" type="date" id="dueDate" />
          </div>
        </div>
      </div>

      <button class="save">Save</button>
      <button type="button" class="btn cancel" @click="router.push('/todos')">Cancel</button>
    </form>

    <p v-else>Loading ToDo...</p>
  </div>
</template>

<style scoped>
/* Main form styling */
form {
  display: flex;
  flex-direction: column;
  gap: 20px;
  max-width: 1000px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 10px;
  background-color: #ffffff;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

/* Labels for input fields */
label {
  font-size: 1rem;
  font-weight: bold;
  margin-bottom: 5px;
  color: #333333;
}

/* Input fields and textareas */
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

textarea {
  height: 80px;
  resize: none;
}

/* Styling for the assignee list container */
.assignee-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

/* Individual assignee items */
.assignee-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.assignee-item:hover {
  background-color: #bcffc6;
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

/* Buttons for adding and removing assignees */
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

button.add {
  background-color: #2cec4c;
  color: white;
}

button.add:hover {
  background-color: #2cec2c;
}

button.remove {
  background-color: #e74c3c;
  color: white;
}

button.remove:hover {
  background-color: #c0392b;
}

/* Disabled buttons */
button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

/* Submit and cancel buttons */
button.save {
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

button.save:hover {
  background-color: #24cd40;
  transform: scale(1.05); /* Slight zoom-in effect */
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3); /* Enhanced shadow */
}
button.save:active {
  background-color: #04ba22;
  transform: scale(0.95); /* Slight shrink effect on click */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* Smaller shadow */
}

button.cancel {
  background-color: #e74c3c;
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

button.cancel:hover {
  background-color: #c0392b;
  transform: scale(1.05); /* Slight zoom-in effect */
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3); /* Enhanced shadow */
}

button.cancel:active {
  background-color: #e61801;
  transform: scale(0.95); /* Slight shrink effect on click */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* Smaller shadow */
}

/* Section titles */
h1 {
  text-align: center;
  font-size: 2rem;
  color: #42b983;
}

/* Adjust form layout */
.form-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 15px;
}

/* Smooth transition for focus and hover states */
input,
textarea,
button {
  transition:
    background-color 0.3s ease,
    box-shadow 0.3s ease,
    color 0.3s ease;
}
</style>
