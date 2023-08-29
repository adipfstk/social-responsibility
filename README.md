<h1>Social Responsability Portal </h1>
<h2>⚡ Quick Overview </h2>
<p>The community-centric portal streamlines issue reporting by allowing users to register directly on the platform. After submitting essential details such as name, email, location coordinates, and preferences, users gain access to a range of features including issue tracking, voting, commenting, and reporting. This approach promotes active community participation and information exchange for efficient issue resolution. </p>
<h2>👉 Features</h2>
<ol>
  <b>
    <li>Registration and Login:</li>
  </b>
  <ul>
    <li>User registration with essential details: fullname, email, location (geographical coordinates), radius of interest, age, gender, and optional profile picture.</li>
  </ul>
  <br>
  <b>
    <li>Visitor Browsing:</li>
  </b>
  <ul>
    <li>View current issues on a map within the user's region of interest.</li>
  </ul>
  <br>
  <b>
    <li>Issue Management:</li>
  </b>
  <ul>
    <li>Create and manage issues with details: title, short description, relevant picture(s), and physical location.</li>
    <li>Confirm or deny the existence of an issue using a thumbs-up and thumbs-down system.</li>
    <li>Upvote and downvote issues to determine their significance.</li>
    <li>Report new issues and track their status.</li>
  </ul>
  <br>
  <b>
    <li>Comments and Feedback:</li>
  </b>
  <ul>
    <li>Provide feedback and comments on issues.</li>
    <li>Edit or delete one's own comments.</li>
    <li>Issues with twice as many downvotes as upvotes are archived automatically.</li>
  </ul>
  <br>
  <b>
    <li>Statistics and Monitoring:</li>
  </b>
  <ul>
    <li>Admin overview of active and archived issues.</li>
    <li>Delete inappropriate comments.</li>
    <li>Archive issues as needed.</li>
    <li>Promote users to admin status.</li>
  </ul>
</ol>
<h2>🔧API Endpoints</h2>
<ol>
  <b><li>/api/v1/user</li><b>
  <details>
    <table>
      <tr>
        <th>Path</th>
        <th>Method</th>
        <th>QueryParam</th>
        <th>Description</th>
      </tr>
      <tr>
        <td>/</td>
        <td>POST</td>
        <td>-</td>
        <td>User registration with optional profile image</td>
      </tr>
      <tr>
        <td>/login</td>
        <td>POST</td>
        <td>-</td>
        <td>User login</td>
      </tr>
      <tr>
        <td>/profile-pic</td>
        <td>GET</td>
        <td>id</td>
        <td>Get user's profile picture by ID</td>
      </tr>
    </table>
  </details>
</ol>
