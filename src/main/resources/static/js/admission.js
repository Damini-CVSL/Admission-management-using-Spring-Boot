// Optional: Confirmation dialogs for Approve/Reject actions

function confirmAction(message) {
    return confirm(message);
}

// Example usage: in your admission.html, add:
// <form ... onsubmit="return confirmAction('Approve this admission?');">
// <form ... onsubmit="return confirmAction('Reject this admission?');">

window.addEventListener("DOMContentLoaded", () => {
    console.log("Admission page loaded. All forms handled via server-side Thymeleaf.");
});
