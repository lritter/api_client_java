package com.animoto.api.error;

/**
 * A ContractError is thrown when the API response message for a given resource does not adhere to the current RESTFUL requirements.<p/>
 *
 * For example, when an API response does not contain Storyboard when the DirectingJob has successfully completed or the creation of a RenderingJob does not contain a link to self from which we can reload resource information to query status.<p/>
 *
 * When this error occurs, please contact Animoto technical support.<p/>
 */
public class ContractError extends Error {

}
