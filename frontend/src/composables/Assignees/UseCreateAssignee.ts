import config from '@/config'
import router from '@/router'
import { showToast, Toast } from '@/ts/toasts'
import { faCheck, faXmark } from '@fortawesome/free-solid-svg-icons'
import { ref } from 'vue'

export function useCreateAssignee() {
  const newPrename = ref('')
  const newName = ref('')
  const newEmail = ref('')

  function createAssignee(prename: string, name: string, email: string) {
    fetch(`${config.apiBaseUrl}/assignees`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ prename, name, email })
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Failed to create assignee')
        }
        return response.json()
      })
      .then(() => {
        showToast(new Toast('Success', `Assignee ${name} created successfully`, 'success', faCheck))
      })
      .catch((error) => {
        showToast(new Toast('Error', error.message, 'error', faXmark))
      })
  }

  function handleSubmit() {
    createAssignee(newPrename.value, newName.value, newEmail.value)
    newPrename.value = ''
    newName.value = ''
    newEmail.value = ''
    router.push('/assignees')
  }
  function cancel() {
    router.push('/assignees')
  }
  return { newPrename, newName, newEmail, handleSubmit, cancel }
}
