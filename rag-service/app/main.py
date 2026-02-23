from fastapi import FastAPI, UploadFile, File, HTTPException
from app.schemas.tokenomics import TokenomicsOutput
from app.pipeline.orchestrator import RagOrchestrator
import tempfile
import os

app = FastAPI(title="Crypto Intelligence RAG Service")
orchestrator = RagOrchestrator()

@app.get("/health")
async def health_check():
    return {"status": "ok"}

@app.post("/api/v1/analyze", response_model=TokenomicsOutput)
async def analyze_document(file: UploadFile = File(...)):
    # Validate file type
    if not file.filename.endswith(('.pdf', '.txt')):
        raise HTTPException(status_code=400, detail="Only PDF and TXT files are supported")
    
    # Save uploaded file to a temporary location
    try:
        with tempfile.NamedTemporaryFile(delete=False, suffix=os.path.splitext(file.filename)[1]) as tmp:
            tmp.write(await file.read())
            tmp_path = tmp.name
        
        # Process through RAG pipeline
        result = await orchestrator.process_document(tmp_path)
        
        # Cleanup
        os.remove(tmp_path)
        
        return result
    except Exception as e:
        # Specific cleanup if path exists
        if 'tmp_path' in locals() and os.path.exists(tmp_path):
            os.remove(tmp_path)
        raise HTTPException(status_code=500, detail=str(e))
