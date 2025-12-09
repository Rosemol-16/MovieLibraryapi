import { useEffect, useState } from "react";

export default function Movies(){
  const [movies,setMovies] = useState([]);
  const [page,setPage] = useState(0);
  const [size,setSize] = useState(5);
  const [sortBy,setSortBy] = useState("title");
  const [dir,setDir] = useState("ASC");
  const [totalPages,setTotalPages] = useState(0);

  useEffect(()=> { fetchPage(); }, [page, size, sortBy, dir]);

  async function fetchPage(){
    const token = localStorage.getItem("token");
    const q = `?page=${page}&size=${size}&sortBy=${sortBy}&dir=${dir}`;
    const res = await fetch("http://localhost:8080/api/movies" + q, {
      headers: { Authorization: `Bearer ${token}` }
    });
    if (!res.ok) {
      alert("Error fetching movies");
      return;
    }
    const data = await res.json();
    setMovies(data.content);
    setTotalPages(data.totalPages);
  }

  return (
    <div>
      <h2>Movies</h2>
      <div>
        Sort: 
        <select onChange={e=>setSortBy(e.target.value)} value={sortBy}>
          <option value="title">Title</option>
          <option value="releaseDate">Release Date</option>
          <option value="rating">Rating</option>
        </select>
        <button onClick={()=>setDir(dir==="ASC"?"DESC":"ASC")}>{dir}</button>
      </div>
      <ul>
        {movies.map(m => <li key={m.id}>{m.title} — {m.director} ({m.genre})</li>)}
      </ul>

      <div>
        <button onClick={()=>setPage(p => Math.max(0,p-1))} disabled={page===0}>Prev</button>
        <span> Page {page+1} of {totalPages} </span>
        <button onClick={()=>setPage(p => Math.min(totalPages-1, p+1))} disabled={page+1>=totalPages}>Next</button>
      </div>
      <button onClick={()=>{ localStorage.removeItem("token"); window.location="/login";}}>Logout</button>
    </div>
  );
}
