package edu.alonso.banddatabase

import android.content.Context

/**
 * A Singleton repository responsible for managing and providing access to a list of [Band] objects.
 *
 * This class loads band data (names and descriptions) from string resources defined
 * in the application's resources (e.g., arrays.xml) when it is first initialized.
 * It ensures that only one instance of the repository exists throughout the application's
 * lifecycle, providing a global point of access to the band data.
 */
class Repository private constructor(context: Context) { // The private constructor restricts instantiation to within this class, essential for the Singleton pattern.
    // The 'context' parameter is used to access application resources like string arrays.

    /**
     * A mutable list that holds all the [Band] objects loaded and managed by this repository.
     * This list is populated during the initialization of the repository.
     */
    var bandList: MutableList<Band> = mutableListOf()

    /**
     * Companion object to implement the Singleton pattern for the [Repository].
     * It provides a static method [getInstance] to obtain the single instance of the [Repository].
     */
    companion object {
        /**
         * The single, static instance of the [Repository].
         * It is nullable and initialized to null, meaning the instance is created lazily on the first call to [getInstance].
         *
         * @Volatile - While not present in the original code, for true thread-safety in a more complex Singleton,
         *             this field would typically be marked with @Volatile if using double-checked locking,
         *             though the original simpler implementation doesn't strictly require it if getInstance
         *             synchronization is robust or if used in a single-threaded context initially.
         */
        private var instance: Repository? = null

        /**
         * Returns the single instance of the [Repository].
         *
         * If the instance does not yet exist, this method creates it using the provided [context].
         * Subsequent calls will return the already created instance.
         * Note: The original implementation is not inherently thread-safe for concurrent first-time access.
         * For robust thread-safety, `synchronized` blocks or other concurrency primitives would be needed
         * around the instance creation logic if multiple threads could call this simultaneously.
         *
         * @param context The application or activity context, used to access resources.
         *                It's generally recommended to use the application context (`context.applicationContext`)
         *                when storing a context in a long-lived object like a Singleton to avoid memory leaks.
         * @return The singleton [Repository] instance. The non-null assertion (`!!`) implies that
         *         after this method, `instance` is guaranteed to be non-null.
         */
        fun getInstance(context: Context): Repository {
            if (instance == null) { // Checks if the instance has been initialized.
                // If not, a new Repository object is created.
                // The application context should ideally be used here to avoid leaking Activity contexts: instance = Repository(context.applicationContext)
                instance = Repository(context)
            }
            return instance!! // Returns the existing or newly created instance.
        }
    }

    /**
     * Initialization block that is executed when a [Repository] instance is created.
     * This block is responsible for populating the [bandList] with [Band] objects
     * by reading data from string arrays defined in the application's resources
     * (e.g., `R.array.bands` and `R.array.descriptions`).
     */
    init {
        // Retrieves the array of band names from the application's string resources.
        val bands = context.resources.getStringArray(R.array.bands)
        // Retrieves the array of band descriptions from the application's string resources.
        val descriptions = context.resources.getStringArray(R.array.descriptions)

        // Iterates through the 'bands' array using their indices.
        // This loop assumes that the 'bands' and 'descriptions' arrays have a one-to-one correspondence
        // and are of the same length.
        for (i in bands.indices) {
            // Creates a new 'Band' object for each entry.
            // The ID for the band is generated based on its index in the array (i + 1).
            // The name and description are taken from the corresponding positions in the 'bands' and 'descriptions' arrays.
            // The newly created 'Band' object is then added to the 'bandList'.
            bandList.add(Band(i + 1, bands[i], descriptions[i]))
        }
    }

    /**
     * Retrieves a specific [Band] from the [bandList] based on its unique ID.
     *
     * @param bandId The integer ID of the band to find.
     * @return The [Band] object if a band with the given [bandId] is found in the [bandList];
     *         otherwise, returns `null`.
     */
    fun getBand(bandId: Int): Band? {
        // Uses the 'find' standard library extension function on the 'bandList'.
        // The lambda expression `{ it.id == bandId }` is a predicate that checks
        // if the 'id' property of a 'Band' object ('it') matches the provided 'bandId'.
        // 'find' returns the first element satisfying the predicate, or null if no such element is found.
        return bandList.find { it.id == bandId }
    }
}

