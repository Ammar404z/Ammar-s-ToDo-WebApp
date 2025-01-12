import config from '@/config'
import router from '@/router'
import { showToast, Toast } from '@/ts/toasts'
import { faCheck, faXmark } from '@fortawesome/free-solid-svg-icons'
import { ref } from 'vue'

/**
 * This composable handles the logic for creating a new assignee.
 */
export function useCreateAssignee() {
  const newPrename = ref('')
  const newName = ref('')
  const newEmail = ref('')

  /**
   * Sends a POST request to create a new assignee with the provided details.
   *
   * @param prename - First name of the assignee.
   * @param name - Last name of the assignee.
   * @param email - Email address of the assignee.
   */
  function createAssignee(prename: string, name: string, email: string): Promise<void> {
    return fetch(`${config.apiBaseUrl}/assignees`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ prename, name, email })
    }).then((response) => {
      if (!response.ok) {
        throw new Error('Failed to create assignee')
      }
      return response.json()
    })
  }

  /**
   * Submits the new assignee form, creates the assignee, and redirects to the assignees list.
   */
  async function handleSubmit() {
    try {
      // Attempt to create the assignee
      await createAssignee(newPrename.value, newName.value, newEmail.value)

      // Reset the form fields
      newPrename.value = ''
      newName.value = ''
      newEmail.value = ''

      // Redirect to the assignees list
      router.push('/assignees')
      showToast(new Toast('Success', 'Assignee created successfully', 'success', faCheck))
    } catch (error: any) {
      // Handle errors without redirecting
      showToast(new Toast('Error', error.message || 'Failed to create assignee', 'error', faXmark))
    }
  }

  function cancel() {
    router.push('/assignees')
  }
  return { newPrename, newName, newEmail, handleSubmit, cancel }
}
