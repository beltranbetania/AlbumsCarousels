package com.example.albumschallenge.utils

/**
 * Normalizes a photo URL from the JSONPlaceholder API by converting it
 * to a valid image URL from the Picsum Photos service.
 *
 * This is useful because `https://via.placeholder.com/` is no longer reliable,
 * so this function generates an equivalent placeholder image using Picsum.
 *
 * @param originalUrl The original URL returned by the API.
 * @param id A unique identifier (used as a fallback seed if the URL does not contain one).
 * @param width The desired image width in pixels. Defaults to 600.
 * @param height The desired image height in pixels. Defaults to 400.
 *
 * @return A valid Picsum Photos URL if the original came from "via.placeholder.com",
 *         otherwise returns the original URL unchanged.
 *
 * Example:
 * ```
 * normalizePhotoUrl("https://via.placeholder.com/600/92c952", 1)
 * // → "https://picsum.photos/seed/92c952/600/400"
 * ```
 */
fun normalizePhotoUrl(originalUrl: String, id: Int, width: Int = 600, height: Int = 400): String {
    if (!originalUrl.contains("via.placeholder.com", ignoreCase = true)) return originalUrl
    val colorSeed = originalUrl.substringAfterLast("/", id.toString())
    return "https://picsum.photos/seed/$colorSeed/$width/$height"
}