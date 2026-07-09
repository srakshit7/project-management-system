const API = "/api";
const STAGES = ["REQUIREMENT", "FEASIBILITY", "DESIGN", "TESTING", "DEPLOYMENT", "MAINTENANCE"];

let state = { clients: [], employees: [], projects: [] };

// ---------- View switching ----------

document.querySelectorAll(".rail-btn").forEach(btn => {
  btn.addEventListener("click", () => switchView(btn.dataset.view));
});

function switchView(view) {
  document.querySelectorAll(".rail-btn").forEach(b => b.classList.toggle("is-active", b.dataset.view === view));
  document.querySelectorAll(".view").forEach(v => v.classList.toggle("is-active", v.id === `view-${view}`));
}

// ---------- Modal helpers ----------

function openModal(id) {
  if (id === "modal-project") populateClientSelect();
  document.getElementById(id).classList.add("is-open");
}

function closeModal(id) {
  document.getElementById(id).classList.remove("is-open");
}

document.querySelectorAll(".modal-backdrop").forEach(backdrop => {
  backdrop.addEventListener("click", (e) => {
    if (e.target === backdrop) backdrop.classList.remove("is-open");
  });
});

// ---------- Data loading ----------

async function loadAll() {
  const [clients, employees, projects] = await Promise.all([
    fetch(`${API}/clients`).then(r => r.json()),
    fetch(`${API}/employees`).then(r => r.json()),
    fetch(`${API}/projects`).then(r => r.json())
  ]);
  state.clients = clients;
  state.employees = employees;
  state.projects = projects;
  renderClients();
  renderEmployees();
  renderProjects();
}

// ---------- Clients ----------

function renderClients() {
  const list = document.getElementById("client-list");
  if (state.clients.length === 0) {
    list.innerHTML = `<p class="empty-note">No clients yet. Add one to get started.</p>`;
    return;
  }
  list.innerHTML = state.clients.map(c => `
    <div class="card">
      <h3>${escapeHtml(c.name)}</h3>
      <div class="meta">Contact: ${escapeHtml(c.contact)}</div>
      <div class="meta">Projects: ${state.projects.filter(p => p.client && p.client.id === c.id).length}</div>
    </div>
  `).join("");
}

async function submitClient(e) {
  e.preventDefault();
  const name = document.getElementById("client-name").value;
  const contact = document.getElementById("client-contact").value;
  await fetch(`${API}/clients`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name, contact })
  });
  e.target.reset();
  closeModal("modal-client");
  await loadAll();
  return false;
}

// ---------- Employees ----------

function renderEmployees() {
  const list = document.getElementById("employee-list");
  if (state.employees.length === 0) {
    list.innerHTML = `<p class="empty-note">No employees yet. Add one to build your team.</p>`;
    return;
  }
  list.innerHTML = state.employees.map(emp => `
    <div class="card">
      <h3>${escapeHtml(emp.name)}</h3>
      <div class="meta">${escapeHtml(emp.designation)}</div>
      <div class="team-chips">
        ${(emp.skills || []).map(s => `<span class="chip">${escapeHtml(s)}</span>`).join("")}
      </div>
    </div>
  `).join("");
}

async function submitEmployee(e) {
  e.preventDefault();
  const name = document.getElementById("emp-name").value;
  const designation = document.getElementById("emp-designation").value;
  const skills = splitCsv(document.getElementById("emp-skills").value);
  await fetch(`${API}/employees`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name, designation, skills })
  });
  e.target.reset();
  closeModal("modal-employee");
  await loadAll();
  return false;
}

// ---------- Projects ----------

function populateClientSelect() {
  const select = document.getElementById("proj-client");
  select.innerHTML = state.clients.map(c => `<option value="${c.id}">${escapeHtml(c.name)}</option>`).join("");
}

function renderProjects() {
  const list = document.getElementById("project-list");
  if (state.projects.length === 0) {
    list.innerHTML = `<p class="empty-note">No projects yet. Create one once you have a client.</p>`;
    return;
  }
  list.innerHTML = state.projects.map(p => {
    const stageIndex = STAGES.indexOf(p.tracker.currentStage);
    return `
      <div class="card">
        <span class="tag">${escapeHtml(p.projectType)}</span>
        <h3>${escapeHtml(p.name)}</h3>
        <div class="meta">${p.client ? escapeHtml(p.client.name) : "No client"} &middot; ${p.assignedTeam.length} assigned</div>
        <div class="stage-rail">
          ${STAGES.map((s, i) => `<div class="seg ${i <= stageIndex ? "filled" : ""}"></div>`).join("")}
        </div>
        <div class="stage-label"><span>${p.tracker.currentStage}</span><span>&#8377;${p.billing.amountDue.toFixed(0)} due</span></div>
        <div class="card-actions">
          <button class="btn btn-ghost btn-small" onclick="openDetail(${p.id})">Manage</button>
        </div>
      </div>
    `;
  }).join("");
}

async function submitProject(e) {
  e.preventDefault();
  const name = document.getElementById("proj-name").value;
  const clientId = Number(document.getElementById("proj-client").value);
  const type = document.getElementById("proj-type").value;
  const requirementDescription = document.getElementById("proj-requirement").value;
  const featureList = splitCsv(document.getElementById("proj-features").value);
  await fetch(`${API}/projects`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name, clientId, type, requirementDescription, featureList })
  });
  e.target.reset();
  closeModal("modal-project");
  await loadAll();
  return false;
}

// ---------- Project detail modal ----------

function openDetail(projectId) {
  renderDetail(projectId);
  document.getElementById("modal-detail").classList.add("is-open");
}

function renderDetail(projectId) {
  const p = state.projects.find(x => x.id === projectId);
  const stageIndex = STAGES.indexOf(p.tracker.currentStage);
  const unassigned = state.employees.filter(e => !p.assignedTeam.some(a => a.id === e.id));

  document.getElementById("detail-body").innerHTML = `
    <h2>${escapeHtml(p.name)}</h2>
    <div class="meta">${escapeHtml(p.projectType)} &middot; ${p.client ? escapeHtml(p.client.name) : "No client"}</div>
    <div class="meta">${escapeHtml(p.requirement && p.requirement.description ? p.requirement.description : "No requirement text")}</div>

    <div class="detail-section">
      <h4>Team assignment</h4>
      <div class="team-chips">
        ${p.assignedTeam.map(a => `<span class="chip">${escapeHtml(a.name)} &mdash; ${escapeHtml(a.designation)}</span>`).join("") || '<span class="empty-note">No one assigned yet</span>'}
      </div>
      ${unassigned.length > 0 ? `
        <div class="card-actions">
          <select id="assign-select">
            ${unassigned.map(e => `<option value="${e.id}">${escapeHtml(e.name)} (${escapeHtml(e.designation)})</option>`).join("")}
          </select>
          <button class="btn btn-primary btn-small" onclick="doAssign(${p.id})">Assign</button>
        </div>` : ""}
    </div>

    <div class="detail-section">
      <h4>Lifecycle</h4>
      <div class="stage-rail">
        ${STAGES.map((s, i) => `<div class="seg ${i <= stageIndex ? "filled" : ""}"></div>`).join("")}
      </div>
      <div class="stage-label"><span>${p.tracker.currentStage}</span></div>
      <div class="card-actions">
        ${stageIndex < STAGES.length - 1
          ? `<button class="btn btn-primary btn-small" onclick="doAdvance(${p.id})">Advance to ${STAGES[stageIndex + 1]}</button>`
          : `<span class="empty-note">Final stage reached</span>`}
      </div>
    </div>

    <div class="detail-section">
      <h4>Billing</h4>
      <div class="meta">Rate: &#8377;${p.billing.rate}/hr &middot; Hours logged: ${p.billing.hoursLogged} &middot; Due: &#8377;${p.billing.amountDue.toFixed(0)} &middot; Status: ${p.billing.status}</div>
      <div class="card-actions">
        <input type="number" id="hours-input" placeholder="Hours" min="0" step="0.5" style="width:90px; padding:6px 8px; border-radius:7px; border:1px solid var(--line);">
        <button class="btn btn-ghost btn-small" onclick="doLogHours(${p.id})">Log hours</button>
        <button class="btn btn-ghost btn-small" onclick="doMarkPaid(${p.id})">Mark paid</button>
      </div>
    </div>

    <div class="detail-section">
      <h4>Client feedback</h4>
      ${p.feedbackList.length > 0
        ? p.feedbackList.map(f => `<div class="feedback-item"><strong>${f.stage}</strong> &middot; ${"&#9733;".repeat(f.rating)} &mdash; ${escapeHtml(f.comment)}</div>`).join("")
        : '<p class="empty-note">No feedback logged yet.</p>'}
      <div class="card-actions">
        <input type="text" id="feedback-comment" placeholder="Comment" style="flex:1; padding:6px 8px; border-radius:7px; border:1px solid var(--line);">
        <select id="feedback-rating">
          <option value="5">5</option><option value="4">4</option><option value="3">3</option><option value="2">2</option><option value="1">1</option>
        </select>
        <button class="btn btn-ghost btn-small" onclick="doFeedback(${p.id})">Submit</button>
      </div>
    </div>

    <div class="modal-actions">
      <button class="btn btn-ghost" onclick="closeModal('modal-detail')">Close</button>
    </div>
  `;
}

async function doAssign(projectId) {
  const employeeId = Number(document.getElementById("assign-select").value);
  await fetch(`${API}/projects/${projectId}/assign`, {
    method: "POST", headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ employeeId })
  });
  await loadAll();
  renderDetail(projectId);
}

async function doAdvance(projectId) {
  await fetch(`${API}/projects/${projectId}/advance-stage`, { method: "POST" });
  await loadAll();
  renderDetail(projectId);
}

async function doLogHours(projectId) {
  const hours = Number(document.getElementById("hours-input").value || 0);
  if (hours <= 0) return;
  await fetch(`${API}/projects/${projectId}/billing/hours`, {
    method: "POST", headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ hours })
  });
  await loadAll();
  renderDetail(projectId);
}

async function doMarkPaid(projectId) {
  await fetch(`${API}/projects/${projectId}/billing/pay`, { method: "POST" });
  await loadAll();
  renderDetail(projectId);
}

async function doFeedback(projectId) {
  const comment = document.getElementById("feedback-comment").value;
  const rating = Number(document.getElementById("feedback-rating").value);
  if (!comment) return;
  await fetch(`${API}/projects/${projectId}/feedback`, {
    method: "POST", headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ comment, rating })
  });
  await loadAll();
  renderDetail(projectId);
}

// ---------- Utilities ----------

function splitCsv(value) {
  return value.split(",").map(s => s.trim()).filter(Boolean);
}

function escapeHtml(str) {
  if (str === null || str === undefined) return "";
  return String(str).replace(/[&<>"']/g, m => ({
    "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#39;"
  }[m]));
}

loadAll();
