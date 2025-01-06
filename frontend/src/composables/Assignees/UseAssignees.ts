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
export function useAssignees() {
  // Fetch all assignees
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

  // Delete an assignee
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
