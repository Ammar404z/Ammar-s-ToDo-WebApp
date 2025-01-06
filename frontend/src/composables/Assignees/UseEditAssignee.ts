import config from '@/config'
import { showToast, Toast } from '@/ts/toasts'
import { faCheck, faXmark } from '@fortawesome/free-solid-svg-icons'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

interface Assignee {
  id: number
  prename: string
  name: string
  email: string
}

export function useAssignee() {
  const router = useRouter()
  const assignee = ref<Assignee | null>(null)
  const loading = ref(true)

  const fetchAssigneeById = async (id: number) => {
    try {
      const response = await fetch(`${config.apiBaseUrl}/assignees/${id}`)
      if (!response.ok) {
        throw new Error(`Failed to fetch assignee with ID ${id}: ${response.statusText}`)
      }
      const fetchedAssignee = await response.json()
      if (fetchedAssignee) {
        assignee.value = fetchedAssignee
      } else {
        throw new Error('Assignee not found')
      }
    } catch (error: any) {
      console.error('Error fetching assignee:', error)
      showToast(new Toast('Error', error.message, 'error', faXmark))
    } finally {
      loading.value = false
    }
  }

  const updateAssignee = async () => {
    if (!assignee.value) return

    try {
      const response = await fetch(`${config.apiBaseUrl}/assignees/${assignee.value.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(assignee.value)
      })
      if (!response.ok) {
        throw new Error('Failed to update assignee')
      }
      showToast(new Toast('Success', 'Assignee updated successfully', 'success', faCheck))
      router.push('/assignees')
    } catch (error: any) {
      showToast(new Toast('Error', error.message, 'error', faXmark))
    }
  }

  return {
    assignee,
    loading,
    fetchAssigneeById,
    updateAssignee
  }
}
