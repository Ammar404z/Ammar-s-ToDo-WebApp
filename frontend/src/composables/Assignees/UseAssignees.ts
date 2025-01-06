import config from '@/config'
import { showToast, Toast } from '@/ts/toasts'
import { faCheck, faXmark } from '@fortawesome/free-solid-svg-icons'
import { ref, type Ref } from 'vue'

interface Assignee {
  id: number
  prename: string
  name: string
  email: string
}

const assignees: Ref<Assignee[]> = ref([])

/**
 * This composable manages all logic related to fetching, listing, and deleting assignees.
 */
export function useAssignees() {
  /**
   * Fetches all available assignees from the backend and updates the `assignees` reactive property.
   */
  function fetchAllAssignees() {
    fetch(`${config.apiBaseUrl}/assignees`)
      .then((response) => {
        if (!response.ok) {
          throw new Error('Failed to fetch assignees')
        }
        return response.json()
      })
      .then((data: Assignee[]) => {
        assignees.value = data
      })
      .catch((error) => {
        console.error('Error fetching assignees:', error)
        showToast(new Toast('Error', error.message, 'error', faXmark, 10))
      })
  }

  /**
   * Deletes a specific assignee by their ID after user confirmation.
   *
   * @param id - The ID of the assignee to delete.
   */
  function deleteAssignee(id: number) {
    if (confirm('Are you sure you want to delete this assignee?')) {
      fetch(`${config.apiBaseUrl}/assignees/${id}`, {
        method: 'DELETE'
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error('Failed to delete assignee')
          }
          showToast(new Toast('Success', 'Assignee deleted successfully', 'success', faCheck))
          assignees.value = assignees.value.filter((assignee) => assignee.id !== id)
        })
        .catch((error) => {
          showToast(new Toast('Error', error.message, 'error', faXmark))
        })
    }
  }
  return { assignees, fetchAllAssignees, deleteAssignee }
}
