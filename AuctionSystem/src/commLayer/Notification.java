package commLayer;

/**
 * An enumeration of the different types of notification
 *
 */
public enum Notification
{
	ITEM_RECIEVED, BID_RECIEVED, USER_RECIEVED, PROPERTY_RECIEVED, ITEM_REQUEST_RECIEVED, USER_REQUEST_RECIEVED, USER_NOT_FOUND, PASSWORD_CORRECT, 
	PASSWORD_INCORRECT, BID_ON_OWN_ITEM, BID_LOWER_THAN_CURRENT, DATABASE_HAS_USER, DATABASE_DOES_NOT_HAVE_USER, BID_LOWER_THAN_RESERVE
}
