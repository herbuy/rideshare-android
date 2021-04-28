package cache;

//although this class adds no functionality
//all caches shd extend this class, so we can track
//all caches created even if they are in stored in different project folders or packages.
//It is useful in case we need to do things
//like deleting all caches.
//we would just view the class Hierarchy
//to see all classes that inherit from this class
//then we can call clear() on each of them in one function
//in order to delete all caches
//this useful if, say, user switches account
// and we dont want them to keep the old user's data
//on their device coz they may end up
//viewing data which is not theirs, and they may
//even not know it is not theirs
//so the views would end up showing them irrelevant data.
//This is bad for both user experience and security
public abstract class LocalCache<T> extends AppDatabase<T> {

}
