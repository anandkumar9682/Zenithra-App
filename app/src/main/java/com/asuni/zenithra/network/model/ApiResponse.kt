package com.asuni.zenithra.network.model

/**
 * Author: Anand Kumar
 * Email: anandkumar.docs@gmail.com
 * Date: 2025-04-13
 * Description: This sealed class represents the various states of an API response,
 * including loading, success, error, and clear states.
 *
 * @param T The type of data being returned by the API call.
 * @property data The response data, if the request is successful.
 * @property errorMessage The error message, if the request fails.
 */
sealed class ApiResponse<T>(val data: T? = null, val errorMessage: String? = null) {

    /**
     * Represents a loading state while waiting for the API response.
     */
    class Loading<T> : ApiResponse<T>()

    /**
     * Represents a successful API response with data.
     * @param data The data received from the API.
     */
    class Success<T>(data: T? = null) : ApiResponse<T>(data = data)

    /**
     * Represents an error state when the API request fails.
     * @param errorMessage The error message from the API or failure.
     * @param data Optional data that may be returned in case of a partial success.
     */
    class Error<T>(errorMessage: String, data: T? = null) : ApiResponse<T>(errorMessage = errorMessage, data = data)

    /**
     * Represents a state to clear or reset the current response.
     */
    class Clear<T> : ApiResponse<T>()
}
