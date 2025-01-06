import config from '@/config'
import { showToast, Toast } from '@/ts/toasts'
import { faCheck, faXmark } from '@fortawesome/free-solid-svg-icons'
import { computed, ref, type Ref } from 'vue'
import { useRouter } from 'vue-router'

interface ToDo {
  id: number
  title: string
  description: string
  finished: boolean
  assigneeList: { id: number; prename: string; name: string }[]
  createdDate: number
  dueDate: number
  finishedDate: number | null
  category: string
}

/**
 * This composable manages the logic for handling ToDo items, including fetching, filtering, sorting, and updating them.
 */
export function useToDo() {
  // Reactive properties
  const toDos: Ref<ToDo[]> = ref([])
  const showFinished = ref(false)
  const router = useRouter()
  const searchQuery = ref('')
  const currentSort = ref('titleAscending') // Default sorting method

  // Computed properties for filtering and sorting
  const finishedToDos = computed(() => toDos.value.filter((todo) => todo.finished))
  const unFinishedToDos = computed(() => toDos.value.filter((todo) => !todo.finished))

  const filteredUnfinishedToDos = computed(() => {
    return unFinishedToDos.value
      .filter((todo) => todo.title.toLowerCase().includes(searchQuery.value.toLowerCase()))
      .sort((a, b) => sortToDos(a, b, currentSort.value))
  })

  const filteredFinishedToDos = computed(() => {
    return finishedToDos.value
      .filter((todo) => todo.title.toLowerCase().includes(searchQuery.value.toLowerCase()))
      .sort((a, b) => sortToDos(a, b, currentSort.value))
  })

  // Sorting function
  function sortToDos(a: ToDo, b: ToDo, sortType: string) {
    switch (sortType) {
      case 'titleAscending':
        return a.title.localeCompare(b.title)
      case 'titleDescending':
        return b.title.localeCompare(a.title)
      case 'dueDateAscending':
        return a.dueDate - b.dueDate
      case 'dueDateDescending':
        return b.dueDate - a.dueDate
      default:
        return 0 // No sorting if sortType is unknown
    }
  }

  /**
   * Fetches all ToDo items from the backend and updates the `toDos` reactive property.
   */
  function fetchTodos() {
    fetch(`${config.apiBaseUrl}/todos`)
      .then((response) => {
        if (!response.ok) {
          throw new Error('Failed to fetch Todos')
        }
        return response.json()
      })
      .then((data: ToDo[]) => {
        toDos.value = data
      })
      .catch((error) => {
        showToast(new Toast('Error', error.message, 'error', faXmark, 10))
      })
  }

  function formatDate(timestamp: number | null | undefined): string {
    if (!timestamp) {
      // Handle null or undefined timestamp
      return ''
    }
    const date = new Date(timestamp)
    // Ensure the date is valid before formatting
    if (isNaN(date.getTime())) {
      return 'Invalid Date'
    }
    return date.toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    })
  }

  /**
   * Marks a ToDo as finished or unfinished and updates its `finishedDate` accordingly.
   *
   * @param todo - The ToDo item to toggle.
   */
  async function setFinished(todo: ToDo) {
    // Toggle the finished status
    todo.finished = !todo.finished

    // Update the finishedDate based on the new finished status
    todo.finishedDate = todo.finished ? Date.now() : null

    try {
      // Prepare the payload with all current data, ensuring the assignee list is preserved
      const payload = {
        id: todo.id,
        title: todo.title,
        description: todo.description,
        finished: todo.finished,
        assigneeIdList: todo.assigneeList.map((assignee) => assignee.id), // Map assigneeList to IDs
        createdDate: todo.createdDate,
        dueDate: todo.dueDate,
        finishedDate: todo.finishedDate,
        category: todo.category
      }

      // Send the PUT request
      const response = await fetch(`${config.apiBaseUrl}/todos/${todo.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      })

      // Handle response
      if (!response.ok) {
        throw new Error('Failed to update ToDo status')
      }

      showToast(new Toast('Success', 'ToDo status updated successfully', 'success', faCheck, 5))
    } catch (error: any) {
      showToast(new Toast('Error', error.message, 'error', faXmark, 10))
    }
  }

  /**
   * Deletes a ToDo item by its ID after user confirmation.
   *
   * @param id - The ID of the ToDo to delete.
   */
  function deleteTodo(id: number) {
    if (confirm('Are you sure you want to delete this ToDo?')) {
      fetch(`${config.apiBaseUrl}/todos/${id}`, {
        method: 'DELETE'
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error('Failed to delete ToDo')
          }
          toDos.value = toDos.value.filter((todo) => todo.id !== id)
          showToast(new Toast('Success', 'ToDo deleted successfully', 'success', faCheck, 5))
        })
        .catch((error) => {
          showToast(new Toast('Error', error.message, 'error', faXmark, 10))
        })
    }
  }

  /**
   * Initiates the download of all ToDo items as a CSV file.
   */
  function downloadCsv() {
    fetch(`${config.apiBaseUrl}/csv-downloads/todos`) // Fetch data from the backend endpoint
      .then((response) => {
        if (!response.ok) {
          throw new Error('Failed to download CSV')
        }
        return response.blob() // Convert the response into a Blob (binary large object)
      })
      .then((blob) => {
        const url = window.URL.createObjectURL(blob) // Create a temporary URL for the blob
        const a = document.createElement('a') // Create an anchor element
        a.href = url // Assign the blob URL to the anchor's href
        a.download = 'todos.csv' // Specify the filename for the download
        document.body.appendChild(a) // Add the anchor to the DOM
        a.click() // Simulate a click to trigger the download
        a.remove() // Remove the anchor from the DOM after triggering the download
      })
      .catch((error) => {
        showToast(new Toast('Error', error.message, 'error', faXmark, 10)) // Handle errors and show a toast notification
      })
  }
  return {
    toDos,
    showFinished,
    router,
    searchQuery,
    currentSort,
    finishedToDos,
    unFinishedToDos,
    filteredUnfinishedToDos,
    filteredFinishedToDos,
    sortToDos,
    fetchTodos,
    formatDate,
    setFinished,
    deleteTodo,
    downloadCsv
  }
}
