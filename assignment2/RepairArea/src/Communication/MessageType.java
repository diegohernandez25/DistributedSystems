package Communication;
/**
 * Message Types
 * */
public enum MessageType {

    //Lounge

    ENTER_CUSTOMER_QUEUE,
    ATTEND_CUSTOMER,
    GET_REPLACEMENT_CAR_KEY,
    RETURN_REPLACEMENT_CAR_KEY,
    EXIT_LOUNGE,
    IS_CUSTOMER_CAR_KEYS_EMPTY,
    GIVE_MANAGER_CAR_KEY,
    PAY_FOR_THE_SERVICE,
    GET_CAR_TO_REPAIR_KEY,
    REQUEST_PART,
    REGISTER_STOCK_REFILL,
    CHECKS_PARTS_REQUEST,
    ALERT_MANAGER_REPAIR_DONE,
    READY_TO_DELIVER_KEY,
    REQUEST_NUMBER_PART,
    ALL_DONE,
    OK,
    ATTEND_CUSTOMER_RES,
    GET_REPLACEMENT_CAR_KEY_RES,
    PAY_FOR_THE_SERVICE_RES,
    GET_CAR_TO_REPAIR_KEY_RES,
    CHECKS_PARTS_REQUEST_RES,
    REQUEST_NUMBER_PART_RES,
    ALL_DONE_RES,
    GET_CUSTOMER_FROM_KEY,
    GET_CUSTOMER_FROM_KEY_RES,
    ARE_CARS_FIXED,
    ARE_CARS_FIXED_RES,
    GET_FIXED_CAR_KEY,
    GET_FIXED_CAR_KEY_RES,
    FINISH,

    //Park

    GET_CAR,
    GET_CAR_RES,
    PARK_CAR,

    //Outside World

    WAIT_FOR_REPAIR,
    ALERT_CUSTOMER,
    ALERT_REMAINING_CUSTOMERS,
    IS_CUSTOMER_IN_OW,
    IS_CUSTOMER_IN_OW_RES,

    //Repair Area

    CHECK_CAR,
    CHECK_CAR_RES,
    REPAIR_CAR,
    REPAIR_CAR_RES,
    REPAIR_WAITING_CAR_WITH_PARTS_AVAILABLE,
    REPAIR_WAITING_CAR_WITH_PARTS_AVAILABLE_RES,
    CONCLUDE_CAR_REPAIR,
    REFILL_CAR_PART_STOCK,
    GET_MAX_PART_STOCK,
    GET_MAX_PART_STOCK_RES,
    FIND_NEXT_TASK,
    FIND_NEXT_TASK_RES,
    POST_JOB,
    SEND_HOME,

    //Supplier Site
    RESTOCK_PART,
    RESTOCK_PART_RES,

    //General Repository Information

    SET_STATE_MANAGER,
    SET_STATE_CUSTOMER,
    SET_STATE_MECHANIC,
    SET_CUSTOMER_VEHICLE,
    SET_CUSTOMER_NEEDS_REPLACEMENT,
    SET_CUSTOMER_CAR_REPAIRED,
    ADD_CUSTOMERS_QUEUE,
    REMOVE_CUSTOMERS_QUEUE,
    ADD_CUSTOMERS_REPLACEMENT_QUEUE,
    REMOVE_CUSTOMERS_REPLACEMENT_QUEUE,
    SET_NUM_CARS_REPAIRED,
    SET_NUM_CARS_PARKED,
    SET_NUM_REPLACEMENT_PARKED,
    SET_NUM_POST_JOBS,
    ADD_NUM_PART_AVAILABLE,
    REMOVE_NUM_PART_AVAILABLE,
    SET_NUM_CAR_WAITING_PART,
    SET_FLAG_MISSING_PART,
    SET_NUM_BOUGHT_PART;

}