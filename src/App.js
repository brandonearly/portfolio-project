import picture from './picture.jpg';
import './App.css';
import React, {useState} from "react";

function App() {
  
  const[showContent,setShowContent] = useState(false);

  const handleStartClick = () => {
    setShowContent(true);
  };


  return (
    <div>
      {!showContent ? (
        <div className = "intro-container">
          <p>Hi there!</p>
          <p>my name is Brandon</p>
          <p>and this is a project of mine</p>
          <button onClick={handleStartClick}>Get to know me!</button>
        </div>
      ) : (
    
        <div className = "container">
          <div className= "my-picture-container">
            <img src={picture} className = "my-picture" alt ="Brandon"></img>
          </div>
          <div className="text-container">
            <p>Hello, my name is Brandon Early, and I am currently a 19 year-old first year computer science student at Michigan Technological University. In my spare time I love playing hockey, watching movies, listening to music and hanging out with my friends. </p>

            <p>Beyond my personal interests, I also have a passion for computers, programming and problem solving thorugh code. In my first year of studies at MTU, as well as part of my work thorugh high school, I have developed a variety of skills in programming and software development, as well as logical thinking and analysis. Some of these skills include Java and C programming, and while I have learned and utilized these almost entirely in the classroom so far, as a result they are some of my most developed programming skills. Through my own personal projects, clubs and curiousity, I have also become somewhat capable with Javascript and React, particularly for front-end design (this project is written all in JS and react! ...and the css styles).</p>

           <p>Obviously the hard skills are important, but what I think helps set me apart, is my soft skills. Working at a restaurant while also playing Varsity Hockey through highschool helped me learn vital communication, teamwork, organization and time management skills. Most importantly though, these experiences taught me the value of hard work and tenacity, as well as the importance of kindness, manners and jsut being generally personable.</p>

            <p>Transitioning to college over the past year has been without a shadow of a doubt the most significant adjustment of my life, and it has presented challenges, new and old. From tasks as big as paying for college itself, to trivial chores like doing laundry in the dorms, my first semesters at college have given me renewed self assurance, a fresh, burning passion for learning and programming as well as the confidence to believe that I would be an excellent addition to your company regardless of the capacity of that role.</p>

            <p>I hope that this little portfolio of mine helped give you a better understanding of who I am as a person and the skills I bring to the table. I appreciate your time and hope you have a fantastic day!</p>
         </div>
        </div>
      )}
    </div>
  );
}

export default App;
