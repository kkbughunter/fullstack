import { useState } from 'react'


function App() {
  const [count, setCount] = useState(0)

  return (
    <div class="min-h-screen flex items-center justify-center bg-gray-900">
      <p class="text-white text-3xl font-bold">
        Hello Welcome the Fullstack Application...
      </p>
    </div>
  )
}

export default App
