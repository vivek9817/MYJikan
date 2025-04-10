An Android movie detail screen UI built using Kotlin and XML that displays trailer, poster fallback,
genre, cast, and synopsis. Includes dynamic video loading via WebView and image loading with Glide.

This module displays a detailed view of a selected movie or anime, including:

- Trailer playback using WebView
- Fallback poster image
- Genre, rating, and score with star icon
- Cast and episode count
- Plot/synopsis


1. Layout Design (XML)

- `NestedScrollView` for vertical scrolling
- `FrameLayout` to layer:
    - `WebView` for trailer
    - `ImageView` as fallback poster
    - Gradient Play Button
    - Genre & Rating Chip
- Cast and Episode section using `LinearLayout`
- Title and Description sections