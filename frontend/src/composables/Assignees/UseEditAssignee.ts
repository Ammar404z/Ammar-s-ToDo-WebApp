import config from '@/config'
import { showToast, Toast } from '@/ts/toasts'
import { faCheck, faXmark } from '@fortawesome/free-solid-svg-icons'

export interface Assignee {
  id: number
  prename: string
  name: string
  email: string
}

/**
 * Fetches an assignee by ID from the backend.
 * @param id - The ID of the assignee to fetch.
 * @returns A promise resolving to the assignee.
 */
export async function fetchAssigneeById(id: number): Promise<Assignee> {
  try {
    const response = await fetch(`${config.apiBaseUrl}/assignees/${id}`)
    if (!response.ok) {
      throw new Error(`Failed to fetch assignee with ID ${id}: ${response.statusText}`)
    }
    return await response.json()
  } catch (error: any) {
    showToast(new Toast('Error', error.message, 'error', faXmark))
    throw error
  }
}

/**
 * Updates an assignee in the backend.
 * @param assignee - The assignee object to update.
 */
export async function updateAssignee(assignee: Assignee): Promise<void> {
  try {
    const response = await fetch(`${config.apiBaseUrl}/assignees/${assignee.id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(assignee)
    })
    if (!response.ok) {
      throw new Error('Failed to update assignee')
    }
    showToast(new Toast('Success', `Assignee updated successfully`, 'success', faCheck))
  } catch (error: any) {
    showToast(new Toast('Error', error.message, 'error', faXmark))
    throw error
  }
}
