import config from '@/config'
import { showToast, Toast } from '@/ts/toasts'
import { faCheck, faXmark } from '@fortawesome/free-solid-svg-icons'
import { ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'

interface Assignee {
  id: number
  prename: string
  name: string
}

interface ToDo {
  id: number
  title: string
  description: string
  finished: boolean
  assigneeList: Assignee[]
  createdDate: number
  dueDate: number
  finishedDate: number | null
}

/**
 * This composable manages logic for editing an existing ToDo item.
 */
export function useEditToDo() {
  const route = useRoute()
  const router = useRouter()
  const toDo = ref<ToDo | null>(null)
  const loading = ref(true)
  const allAssignees = ref<Assignee[]>([])

  /**
   * Fetches a ToDo item by its ID and updates the reactive property `toDo`.
   *
   * @param id - The ID of the ToDo to fetch.
   */
  const fetchToDoById = async (id: number) => {
    try {
      const response = await fetch(`${config.apiBaseUrl}/todos/${id}`)
      if (!response.ok) {
        throw new Error(`Failed to fetch ToDo with ID ${id}: ${response.statusText}`)
      }
      toDo.value = await response.json()
      console.log('Fetched ToDo:', toDo.value) // Debug: Check the assigneeList
    } catch (error: any) {
      console.error('Error fetching ToDo:', error)
      showToast(new Toast('Error', error.message, 'error', faXmark))
    } finally {
      loading.value = false
    }
  }

  /**
   * Fetches a list of all available assignees.
   */
  const fetchAllAssignees = async () => {
    try {
      const response = await fetch(`${config.apiBaseUrl}/assignees`)
      if (!response.ok) throw new Error('Failed to fetch assignees')
      allAssignees.value = await response.json()
    } catch (error: any) {
      console.error('Error fetching assignees:', error)
      showToast(new Toast('Error', error.message, 'error', faXmark))
    }
  }

  /**
   * Toggles the selection of an assignee for the ToDo being edited.
   *
   * @param id - The ID of the assignee to toggle.
   */
  const toggleAssignee = (id: number) => {
    if (!toDo.value) return

    // Ensure assigneeList is always initialized
    if (!toDo.value.assigneeList) {
      toDo.value.assigneeList = []
    }

    const isSelected = toDo.value.assigneeList.some((a) => a.id === id)

    if (isSelected) {
      // Remove assignee
      toDo.value.assigneeList = toDo.value.assigneeList.filter((a) => a.id !== id)
    } else {
      // Add assignee
      const assignee = allAssignees.value.find((a) => a.id === id)
      if (assignee) {
        toDo.value.assigneeList.push(assignee) // Use push instead of creating a new array
      }
    }
  }

  const prepareDueDate = (dateString: number) => {
    return new Date(dateString).getTime()
  }

  /**
   * Updates the current ToDo item with the edited data.
   */
  const updateToDo = async () => {
    if (!toDo.value) return

    try {
      const response = await fetch(`${config.apiBaseUrl}/todos/${toDo.value.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          title: toDo.value.title,
          description: toDo.value.description,
          assigneeIdList: toDo.value.assigneeList.map((a) => a.id),
          dueDate: prepareDueDate(toDo.value.dueDate),
          finished: toDo.value.finished
        })
      })

      if (!response.ok) {
        throw new Error(`Failed to update ToDo with ID ${toDo.value.id}: ${response.statusText}`)
      }

      showToast(new Toast('Success', 'ToDo updated successfully', 'success', faCheck))
      router.push('/todos')
    } catch (error: any) {
      showToast(new Toast('Error', error.message, 'error', faXmark))
    }
  }
  return {
    route,
    router,
    toDo,
    loading,
    allAssignees,
    fetchToDoById,
    fetchAllAssignees,
    toggleAssignee,
    prepareDueDate,
    updateToDo
  }
}
