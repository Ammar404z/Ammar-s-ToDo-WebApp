import config from '@/config'
import { showToast, Toast } from '@/ts/toasts'
import { faCheck, faXmark } from '@fortawesome/free-solid-svg-icons'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

export function useCreateTodo() {
  // Form data
  const title = ref('')
  const description = ref('')
  const dueDate = ref('')
  const selectedAssignees = ref<number[]>([]) // Selected Assignee IDs
  const availableAssignees = ref([]) // Fetched list of assignees

  const router = useRouter()

  // Fetch available assignees
  function fetchAvailableAssignees() {
    fetch(`${config.apiBaseUrl}/assignees`)
      .then((response) => {
        if (!response.ok) {
          throw new Error('Failed to fetch assignees')
        }
        return response.json()
      })
      .then((data) => {
        availableAssignees.value = data
      })
      .catch((error) => {
        showToast(new Toast('Error', error.message, 'error', faXmark, 5))
      })
  }

  // Add or remove an assignee
  function toggleAssigneeSelection(assigneeId: number) {
    if (selectedAssignees.value.includes(assigneeId)) {
      selectedAssignees.value = selectedAssignees.value.filter((id) => id !== assigneeId)
    } else {
      selectedAssignees.value.push(assigneeId)
    }
  }

  // Create a new ToDo
  function createToDo() {
    if (!title.value.trim()) {
      showToast(new Toast('Error', 'Please fill in all required fields', 'error', faXmark))
      return
    }

    const newToDo = {
      title: title.value,
      description: description.value,
      dueDate:
        dueDate.value && !isNaN(new Date(dueDate.value).getTime())
          ? new Date(dueDate.value).getTime()
          : null, // Ensure valid timestamp or null
      assigneeIdList: selectedAssignees.value // Assigned IDs
    }

    fetch(`${config.apiBaseUrl}/todos`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newToDo)
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Failed to create ToDo')
        }
        showToast(new Toast('Success', 'ToDo created successfully', 'success', faCheck, 5))
        router.push('/todos') // Redirect to ToDos list
      })
      .catch((error) => {
        showToast(new Toast('Error', error.message, 'error', faXmark, 5))
      })
  }

  return {
    title,
    description,
    dueDate,
    selectedAssignees,
    availableAssignees,
    fetchAvailableAssignees,
    toggleAssigneeSelection,
    createToDo
  }
}
